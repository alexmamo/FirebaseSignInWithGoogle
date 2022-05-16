package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.SIGN_OUT
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*
import ro.alexmamo.firebasesigninwithgoogle.presentation.components.ProgressBar
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.Screen.AuthScreen
import ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components.ProfileContent
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
            ProfileContent(padding)
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