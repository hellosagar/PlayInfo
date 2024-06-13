package dev.sagar.playinfo.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
fun DetailScreen(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize()
      .background(Color.Red),
    contentAlignment = androidx.compose.ui.Alignment.Center
  ) {
  Text(
    text = "Detail Screen",
    modifier = Modifier
      .clickable {
        onClick.invoke()
      }
  )
    }
}

@Preview(showBackground = true)
@Composable
internal fun DetailPreview() {
  PlayInfoTheme {
    DetailScreen({})
  }
}
