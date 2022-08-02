package ro.alexmamo.firebasesigninwithgoogle.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response

interface AuthRepository {
    val isUserAuthenticatedInFirebase: Boolean
    val displayName: String
    val photoUrl: String

    suspend fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun oneTapSignUpWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<Response<Boolean>>

    suspend fun createUserInFirestore(): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    suspend fun revokeAccess(): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>
}