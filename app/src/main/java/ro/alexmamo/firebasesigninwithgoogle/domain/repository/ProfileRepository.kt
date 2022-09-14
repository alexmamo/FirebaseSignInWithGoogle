package ro.alexmamo.firebasesigninwithgoogle.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firebasesigninwithgoogle.domain.model.Response

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    fun signOut(): Flow<Response<Boolean>>

    fun revokeAccess(): Flow<Response<Boolean>>
}