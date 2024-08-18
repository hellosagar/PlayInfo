package dev.sagar.playinfo.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.sagar.playinfo.R
import dev.sagar.playinfo.core.ui.toAnnotatedString
import dev.sagar.playinfo.navigation.Screen
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
fun GameDetailRoute(
    gameId: Screen.Detail,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: DetailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.DetailViewModelFactory> { factory ->
        factory.create(gameId)
    }
) {
    val viewState by viewmodel.viewState.collectAsStateWithLifecycle()
    GameDetailContent(
        modifier = modifier.fillMaxSize(),
        gameDetailViewState = viewState,
        onBackClick = onBackClick,
    )
}

@Composable
private fun GameDetailContent(
    gameDetailViewState: DetailViewState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (gameDetailViewState.isLoading) {
            Text("Loading...")
        } else if (gameDetailViewState.games != null) {
            val gameDetail = gameDetailViewState.games
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .testTag("game_detail_content"),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .testTag("game_detail_image"),
                    model = gameDetail.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                HorizontalDivider(color = Color.Black, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = gameDetail.title.uppercase(),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 26.sp,
                        color = Color.Black,
                        maxLines = 2,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val spanned =
                        HtmlCompat.fromHtml(
                            gameDetail.description,
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        )
                    Text(
                        text = spanned.toAnnotatedString(),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        color = Color.Gray,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rating ${gameDetail.rating} ⭐",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        color = Color.LightGray,
                    )
                }
            }
        } else {
            Text("Error fetching the data")
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(24.dp)
                .size(24.dp)
                .align(Alignment.TopStart)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                tint = Color.Black,
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun DetailPreview() {
    PlayInfoTheme {
        GameDetailRoute(gameId = Screen.Detail(1), onBackClick = { })
    }
}
