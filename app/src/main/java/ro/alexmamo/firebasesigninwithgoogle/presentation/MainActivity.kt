package ro.alexmamo.firebasesigninwithgoogle.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ro.alexmamo.firebasesigninwithgoogle.presentation.auth.AuthViewModel
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.NavGraph
import ro.alexmamo.firebasesigninwithgoogle.presentation.navigation.Screen.ProfileScreen

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            initNavController()
            initNavGraph()
            checkAuthStatus()
        }
    }

    @Composable
    private fun initNavController() {
        navController = rememberAnimatedNavController()
    }

    @Composable
    private fun initNavGraph() {
        NavGraph(
            navController = navController
        )
    }

    private fun checkAuthStatus() {
        if(viewModel.isUserAuthenticated) {
            navController.navigate(ProfileScreen.route)
        }
    }
}