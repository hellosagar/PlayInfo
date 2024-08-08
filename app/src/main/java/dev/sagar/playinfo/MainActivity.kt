package dev.sagar.playinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sagar.playinfo.navigation.Screen
import dev.sagar.playinfo.navigation.SetupNavGraph
import dev.sagar.playinfo.ui.theme.PlayInfoTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserSignedIn.collectLatest {
                    uiState = it
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

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
                        startDestination = when (uiState) {
                            MainActivityUiState.Loading -> {
                                Screen.Onboarding
                            }

                            is MainActivityUiState.Success -> {
                                val isUserLoggedIn = (uiState as? MainActivityUiState.Success)?.isUserLoggedIn
                                if (isUserLoggedIn != null && isUserLoggedIn) {
                                    Screen.Home
                                } else {
                                    Screen.Onboarding
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
