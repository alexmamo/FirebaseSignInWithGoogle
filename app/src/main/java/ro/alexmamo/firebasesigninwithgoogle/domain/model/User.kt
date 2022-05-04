package ro.alexmamo.firebasesigninwithgoogle.domain.model

import java.util.*

data class User(
    var name: String? = null,
    var email: String? = null,
    var photoUrl: String? = null,
    var createdAt: Date? = null
)