package ro.alexmamo.firebasesigninwithgoogle.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithgoogle.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    val oneTapClient: SignInClient
): ViewModel() {
    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase
    val displayName get() = repo.displayName
    val photoUrl get() = repo.photoUrl

    var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var signOutResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun oneTapSignIn() = viewModelScope.launch {
        repo.oneTapSignInWithGoogle().collect { response ->
            oneTapSignInResponse = response
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        repo.firebaseSignInWithGoogle(googleCredential).collect { response ->
            signInWithGoogleResponse = response
        }
    }

    fun signOut() = viewModelScope.launch {
        repo.signOut().collect { response ->
            signOutResponse = response
        }
    }

    fun revokeAccess() = viewModelScope.launch {
        repo.revokeAccess().collect { response ->
            revokeAccessResponse = response
        }
    }
}