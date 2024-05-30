package dev.sagar.playinfo.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
fun HomeScreen(
  name: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize()
      .background(Color.Gray),
    contentAlignment = androidx.compose.ui.Alignment.Center
  ) {

    Text(
      text = "Hello $name!",
      modifier = modifier
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
    HomeScreen("Android", {})
  }
}
