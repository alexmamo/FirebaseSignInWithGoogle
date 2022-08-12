package ro.alexmamo.firebasesigninwithgoogle.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response

interface AuthRepository {
    val isUserAuthenticatedInFirebase: Boolean
    val displayName: String
    val photoUrl: String

    fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<Response<Boolean>>

    fun signOut(): Flow<Response<Boolean>>

    fun revokeAccess(): Flow<Response<Boolean>>
}