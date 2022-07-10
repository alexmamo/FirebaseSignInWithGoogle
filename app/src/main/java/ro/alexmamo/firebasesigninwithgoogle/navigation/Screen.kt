package ro.alexmamo.firebasesigninwithgoogle.navigation

import ro.alexmamo.firebasesigninwithgoogle.core.Constants.AUTH_SCREEN
import ro.alexmamo.firebasesigninwithgoogle.core.Constants.PROFILE_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen: Screen(AUTH_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)
}