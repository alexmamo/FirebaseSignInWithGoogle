package ro.alexmamo.firebasesigninwithgoogle.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Collection References
    const val USERS_REF = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //Buttons
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_OUT = "Sign-out"
    const val REVOKE_ACCESS = "Revoke Access"

    //Screens
    const val AUTH_SCREEN = "Authentication"
    const val PROFILE_SCREEN = "Profile"

    //Messages
    const val SIGN_IN_ERROR_MESSAGE = "16: Cannot find a matching credential."
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
}