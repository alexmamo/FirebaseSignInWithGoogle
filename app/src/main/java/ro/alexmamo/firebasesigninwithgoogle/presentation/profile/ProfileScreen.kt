package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.components.CheckRevokeAccessState
import ro.alexmamo.firebasesigninwithgoogle.components.CheckSignOutState
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.SIGN_OUT
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

    CheckSignOutState(
        signOutResponse = viewModel.signOutResponse,
        navigateToAuthScreen = navigateToAuthScreen
    )

    CheckRevokeAccessState(
        revokeAccessResponse = viewModel.revokeAccessResponse,
        navigateToAuthScreen = navigateToAuthScreen,
        showSnackBar = {
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
    )
}