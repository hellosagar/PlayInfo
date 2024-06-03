package dev.sagar.playinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
        ) { innerPadding ->
          SetupNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}
