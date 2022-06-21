package ro.alexmamo.firebasesigninwithgoogle.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val _signOutState = mutableStateOf<Response<Boolean>>(Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _revokeAccessState = mutableStateOf<Response<Boolean>>(Success(false))
    val revokeAccessState: State<Response<Boolean>> = _revokeAccessState

    val displayName get() = repo.getDisplayName()

    val photoUrl get() = repo.getPhotoUrl()

    fun signOut() = viewModelScope.launch {
        repo.signOut().collect { response ->
            _signOutState.value = response
        }
    }

    fun revokeAccess() = viewModelScope.launch {
        repo.revokeAccess().collect { response ->
            _revokeAccessState.value = response
        }
    }
}