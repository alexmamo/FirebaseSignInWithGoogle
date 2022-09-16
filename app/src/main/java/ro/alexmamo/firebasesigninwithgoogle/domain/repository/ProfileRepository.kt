package ro.alexmamo.firebasesigninwithgoogle.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response

typealias SignOutResponse = Response<Boolean>
typealias RevokeAccessResponse = Response<Boolean>

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    fun signOut(): Flow<SignOutResponse>

    fun revokeAccess(): Flow<RevokeAccessResponse>
}