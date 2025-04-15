package com.example.specime.navigations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.specime.screens.group.groupcreation.GroupCreationScreen
import com.example.specime.screens.authentication.signin.SigninScreen
import com.example.specime.screens.authentication.signup.SignupScreen
import com.example.specime.screens.authentication.changepassword.ChangePasswordSceen
import com.example.specime.screens.account.AccountScreen
import com.example.specime.screens.disc.test.TestScreen
import com.example.specime.screens.disc.testdetail.TestDetailScreen
import com.example.specime.screens.group.groupresult.GroupResultScreen
import com.example.specime.screens.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController(startDestination: String) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("login") { SigninScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("changePassword") { ChangePasswordSceen(navController) }
        composable(
            route = "test?groupId={groupId}",
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )
        ) { backStackEntry ->
            TestScreen(
                navController = navController,
                groupId = backStackEntry.arguments?.getString("groupId")
            )
        }
        composable(
            route = "testDetail/{resultDetailId}",
            arguments = listOf(navArgument("resultDetailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val resultDetailId = backStackEntry.arguments?.getString("resultDetailId") ?: ""
            TestDetailScreen(
                navController = navController,
                resultDetailId = resultDetailId
            )
        }
        composable("groupCreation") { GroupCreationScreen(navController) }
        composable(
            route = "groupResult/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            GroupResultScreen(
                navController = navController,
                groupId = groupId
            )
        }
        composable("account") { AccountScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}