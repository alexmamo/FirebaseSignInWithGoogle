package ro.alexmamo.firebasesigninwithgoogle.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*

@Composable
fun CheckSignOutState(
    signOutResponse: Response<Boolean>,
    navigateToAuthScreen: () -> Unit
) {
    when(signOutResponse) {
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
}