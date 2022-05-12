package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.SIGN_OUT
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*
import ro.alexmamo.firebasesigninwithgoogle.presentation.components.ProgressBar
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.Screen.AuthScreen
import ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components.ProfileTopBar

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.height(48.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(viewModel.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).width(96.dp).height(96.dp)
                )
                Text(
                    text = viewModel.displayName,
                    fontSize = 24.sp
                )
            }
        },
        scaffoldState = scaffoldState
    )

    when(val signOutResponse = viewModel.signOutState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            val signedOut = signOutResponse.data
            signedOut?.let {
                if (signedOut) {
                    LaunchedEffect(signOutResponse.data) {
                        navController.popBackStack()
                        navController.navigate(AuthScreen.route)
                    }
                }
            }
        }
        is Failure -> signOutResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            val accessRevoked = revokeAccessResponse.data
            accessRevoked?.let {
                if (accessRevoked) {
                    LaunchedEffect(revokeAccessResponse.data) {
                        navController.popBackStack()
                        navController.navigate(AuthScreen.route)
                    }
                }
            }
        }
        is Failure -> revokeAccessResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)

                coroutineScope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = REVOKE_ACCESS_MESSAGE,
                        actionLabel = SIGN_OUT
                    )
                    if (result == ActionPerformed) {
                        viewModel.signOut()
                    }
                }
            }
        }
    }
}