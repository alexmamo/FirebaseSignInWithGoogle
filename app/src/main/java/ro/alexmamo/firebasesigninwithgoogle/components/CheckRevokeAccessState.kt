package ro.alexmamo.firebasesigninwithgoogle.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ro.alexmamo.firebasesigninwithgoogle.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.*

@Composable
fun CheckRevokeAccessState(
    revokeAccessResponse: Response<Boolean>,
    navigateToAuthScreen: () -> Unit,
    showSnackBar: () -> Unit
) {
    when(revokeAccessResponse) {
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