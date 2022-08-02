package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.components.ProgressBar
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.SIGN_OUT
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*
import ro.alexmamo.firebasesigninwithgoogle.presentation.auth.AuthViewModel
import ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components.ProfileContent
import ro.alexmamo.firebasesigninwithgoogle.presentation.profile.components.ProfileTopBar

@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToAuthScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ProfileTopBar(
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            ProfileContent(
                padding = padding,
                photoUrl = viewModel.photoUrl,
                displayName = viewModel.displayName
            )
        },
        scaffoldState = scaffoldState
    )

    when(val signOutResponse = viewModel.signOutResponse) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            if (signedOut) {
                LaunchedEffect(signOutResponse.data) {
                    navigateToAuthScreen()
                }
            }
        }
        is Error -> signOutResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    fun showSnackBar() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == ActionPerformed) {
            viewModel.signOut()
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> revokeAccessResponse.data?.let { accessRevoked ->
            if (accessRevoked) {
                LaunchedEffect(revokeAccessResponse.data) {
                    navigateToAuthScreen()
                }
            }
        }
        is Error -> revokeAccessResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
                showSnackBar()
            }
        }
    }
}