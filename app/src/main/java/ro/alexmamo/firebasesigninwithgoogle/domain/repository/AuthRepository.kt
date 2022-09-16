package ro.alexmamo.firebasesigninwithgoogle.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response

typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<Boolean>

interface AuthRepository {
    val isUserAuthenticatedInFirebase: Boolean

    fun oneTapSignInWithGoogle(): Flow<OneTapSignInResponse>

    fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<SignInWithGoogleResponse>
}