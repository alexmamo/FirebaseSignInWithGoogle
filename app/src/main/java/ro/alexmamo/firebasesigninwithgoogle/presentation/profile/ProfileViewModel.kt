package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithgoogle.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signOutResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    val displayName get() = repo.getDisplayName()
    val photoUrl get() = repo.getPhotoUrl()

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