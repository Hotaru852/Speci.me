package com.example.specime.navigations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.specime.components.common.BottomNavigationBar
import com.example.specime.screens.auth.signin.SigninScreen
import com.example.specime.screens.auth.signup.SignupScreen
import com.example.specime.screens.auth.confirmation.ConfirmationScreen
import com.example.specime.screens.auth.changepassword.ChangePasswordSceen
import com.example.specime.screens.connections.ConnectionsScreen
import com.example.specime.screens.disc.DISCScreen
import com.example.specime.screens.account.AccountScreen
import com.example.specime.screens.results.ResultsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController() {
    val navController = rememberAnimatedNavController()

    Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navController)
            if (currentRoute in listOf("disc", "results", "connections", "account")) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = "login",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { SigninScreen(navController) }
            composable("signup") { SignupScreen(navController) }
            composable("changePassword") { ChangePasswordSceen(navController) }
            composable(
                "confirmation/{title}/{message}/{buttonText}/{route}",
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("message") { type = NavType.StringType },
                    navArgument("buttonText") { type = NavType.StringType },
                    navArgument("route") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val message = backStackEntry.arguments?.getString("message")
                val buttonText = backStackEntry.arguments?.getString("buttonText") ?: ""
                val navigateToRoute = backStackEntry.arguments?.getString("route") ?: ""

                ConfirmationScreen(
                    navController = navController,
                    title = title,
                    message = message,
                    buttonText = buttonText,
                    route = navigateToRoute
                )
            }
            composable("disc") { DISCScreen(navController) }
            composable("results") { ResultsScreen(navController) }
            composable("connections") { ConnectionsScreen(navController) }
            composable("account") { AccountScreen(navController) }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    return navBackStackEntry?.destination?.route
}