package dev.sagar.playinfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.sagar.playinfo.feature.auth.SignupRoute
import dev.sagar.playinfo.feature.detail.DetailScreen
import dev.sagar.playinfo.feature.home.HomeScreen

@Composable
fun SetupNavGraph(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  startDestination: Screen = Screen.SignUp,
) {
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination,

  ) {
    composable<Screen.SignUp> {
      SignupRoute(
        onSignupClick = {
          navController.navigate(Screen.Home)
        },
        modifier = Modifier,
      )
    }
    composable<Screen.Home> {
      HomeScreen(
        name = "Sagar Khurana",
        onClick = {
          navController.navigate(Screen.Detail("1"))
        }
      )
    }
    composable<Screen.Detail> { backStackEntry ->
      val detail = backStackEntry.toRoute<Screen.Detail>()
      DetailScreen(
        name = detail.id,
        onClick = {
          if (navController.canGoBack) {
            navController.popBackStack()
          }
        }
      )
    }
  }
}

val NavHostController.canGoBack: Boolean
  get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
