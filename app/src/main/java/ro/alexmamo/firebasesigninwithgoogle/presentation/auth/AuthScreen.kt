package ro.alexmamo.firebasesigninwithgoogle.presentation.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*
import ro.alexmamo.firebasesigninwithgoogle.presentation.auth.components.AuthTopBar
import ro.alexmamo.firebasesigninwithgoogle.presentation.auth.components.SignInButton
import ro.alexmamo.firebasesigninwithgoogle.presentation.components.ProgressBar
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.Screen.ProfileScreen

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AuthTopBar()
        },
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.BottomCenter
            ) {
                SignInButton {
                    viewModel.oneTapSignIn()
                }
            }
        }
    )

    val launcher =  rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials)
            } catch (e: ApiException) {
                print(e)
            }
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    when(val oneTapSignInResponse = viewModel.oneTapSignInState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            oneTapSignInResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> {
            oneTapSignInResponse.e?.let {
                LaunchedEffect(Unit) {
                    print(it)
                    viewModel.oneTapSignUp()
                }
            }
        }
    }

    when(val oneTapSignUpResponse = viewModel.oneTapSignUpState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            oneTapSignUpResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> oneTapSignUpResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val signInResponse = viewModel.signInState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            signInResponse.data?.let { isNewUser ->
                if (isNewUser) {
                    LaunchedEffect(isNewUser) {
                        viewModel.createUser()
                    }
                } else {
                    navController.navigate(ProfileScreen.route)
                }
            }
        }
        is Failure -> signInResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val createUserResponse = viewModel.createUserState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            createUserResponse.data?.let { isUserCreated ->
                if (isUserCreated) {
                    navController.navigate(ProfileScreen.route)
                }
            }
        }
        is Failure -> createUserResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }
}