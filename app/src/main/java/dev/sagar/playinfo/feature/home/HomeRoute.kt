package dev.sagar.playinfo.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
fun HomeRoute(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = hiltViewModel(),
) {
  HomeScreen(onClick, modifier)
}

@Composable
private fun HomeScreen(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Gray),
    contentAlignment = Alignment.Center
  ) {

    Text(
      text = "Home Screen",
      modifier = Modifier
        .clickable {
          onClick.invoke()
        }
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun HomePreview() {
  PlayInfoTheme {
    HomeRoute(
      {})
  }
}
