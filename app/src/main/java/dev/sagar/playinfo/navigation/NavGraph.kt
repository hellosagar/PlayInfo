package dev.sagar.playinfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.sagar.playinfo.feature.auth.login.LoginRoute
import dev.sagar.playinfo.feature.auth.onboarding.OnboardingRoute
import dev.sagar.playinfo.feature.auth.signup.SignupRoute
import dev.sagar.playinfo.feature.detail.GameDetailRoute
import dev.sagar.playinfo.feature.home.HomeRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Onboarding,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Screen.Onboarding> {
            OnboardingRoute(
                onSignupClick = {
                    navController.navigate(Screen.SignUp)
                },
                onLoginClick = {
                    navController.navigate(Screen.Login)
                },
            )
        }
        composable<Screen.Login> {
            LoginRoute(
                onShowSnackbar = onShowSnackbar,
                onLoginClick = {
                    navController.navigate(Screen.Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier,
            )
        }
        composable<Screen.SignUp> {
            SignupRoute(
                onShowSnackbar = onShowSnackbar,
                onSignupClick = {
                    navController.navigate(Screen.Home) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier,
            )
        }
        composable<Screen.Home> {
            HomeRoute(onClick = { game ->
                navController.navigate(Screen.Detail(game.id))
            })
        }
        composable<Screen.Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Screen.Detail>()
            GameDetailRoute(gameId = detail, onBackClick = {
                if (navController.canGoBack) {
                    navController.popBackStack()
                }
            })
        }
    }
}

val NavHostController.canGoBack: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
