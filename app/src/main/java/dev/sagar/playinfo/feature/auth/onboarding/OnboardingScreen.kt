package dev.sagar.playinfo.feature.auth.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
internal fun OnboardingRoute(
  onSignupClick: () -> Unit,
  onLoginClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  OnboardingScreen(
    onSignupClick = onSignupClick,
    onLoginClick = onLoginClick,
    modifier = modifier,
  )
}

@Composable
internal fun OnboardingScreen(
  onSignupClick: () -> Unit,
  onLoginClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Button(
      onClick = onSignupClick
    ) {
      Text("Signup")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = onLoginClick
    ) {
      Text("Login")
    }
  }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
  PlayInfoTheme {
    OnboardingScreen(
      onSignupClick = {},
      onLoginClick = {},
    )
  }
}
