package dev.sagar.playinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sagar.playinfo.navigation.SetupNavGraph
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      PlayInfoTheme {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
          snackbarHost = { SnackbarHost(snackbarHostState) },
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
        ) { innerPadding ->
          SetupNavGraph(
            navController = navController,
            onShowSnackbar = { message, actionText ->
              snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionText,
                duration = Short,
              ) == ActionPerformed
            },
            modifier = Modifier.padding(innerPadding),
          )
        }
      }
    }
  }
}
