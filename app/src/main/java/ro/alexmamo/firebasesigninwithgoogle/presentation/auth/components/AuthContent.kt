package ro.alexmamo.firebasesigninwithgoogle.presentation.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier

@Composable
fun AuthContent(
    padding: PaddingValues,
    oneTapSignIn: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = BottomCenter
    ) {
        SignInButton(
            onClick = oneTapSignIn
        )
    }
}