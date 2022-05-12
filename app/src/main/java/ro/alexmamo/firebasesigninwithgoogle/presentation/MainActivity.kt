package ro.alexmamo.firebasesigninwithgoogle.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firebasesigninwithgoogle.presentation.auth.AuthViewModel
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.NavGraph
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.Screen.ProfileScreen

@AndroidEntryPoint
@InternalCoroutinesApi
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            NavGraph(
                navController = navController
            )
            checkAuthStatus()
            getAuthState()
        }
    }

    private fun checkAuthStatus() {
        if(authViewModel.isUserAuthenticated) {
            navController.navigate(ProfileScreen.route)
        }
    }

    private fun getAuthState() = authViewModel.getAuthState()
}