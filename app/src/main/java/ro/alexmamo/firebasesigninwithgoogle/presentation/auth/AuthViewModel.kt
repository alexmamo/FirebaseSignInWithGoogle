package ro.alexmamo.firebasesigninwithgoogle.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    var oneTapSignUpResponse by mutableStateOf<Response<BeginSignInResult>>(Success(null))
        private set
    var signInResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set
    var createUserResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set
    var signOutResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun getAuthState() = liveData(Dispatchers.IO) {
        repo.getFirebaseAuthState().collect { response ->
            emit(response)
        }
    }

    fun oneTapSignIn() = viewModelScope.launch {
        repo.oneTapSignInWithGoogle().collect { response ->
            oneTapSignInResponse = response
        }
    }

    fun oneTapSignUp() = viewModelScope.launch {
        repo.oneTapSignUpWithGoogle().collect { response ->
            oneTapSignUpResponse = response
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        repo.firebaseSignInWithGoogle(googleCredential).collect { response ->
            signInResponse = response
        }
    }

    fun createUser() = viewModelScope.launch {
        repo.createUserInFirestore().collect { response ->
            createUserResponse = response
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