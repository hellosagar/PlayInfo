package dev.sagar.playinfo.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import coil.compose.AsyncImage
import dev.sagar.playinfo.domain.GameItem
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
fun HomeRoute(
    onClick: (gameItem: GameItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    HomeContent(viewState = viewState, onGameClick = onClick)
}

@Composable
private fun HomeContent(
    viewState: HomeViewState,
    onGameClick: (GameItem) -> Unit,
) {
    if (viewState.isLoading) {
        Text(text = "Loading...")
    } else {
        val gamesLazyItems: LazyPagingItems<GameItem> =
            viewState.games?.collectAsLazyPagingItems() ?: return
        GameList(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            gamesLazyItems = gamesLazyItems,
            onGameClick = onGameClick,
        )
    }
}

@Composable
private fun GameList(
    gamesLazyItems: LazyPagingItems<GameItem>,
    onGameClick: (gameItem: GameItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .testTag("home_game_list"),
    ) {
        items(
            count = gamesLazyItems.itemCount,
            contentType = gamesLazyItems.itemContentType { "Games" }
        ) { index: Int ->
            val article: GameItem = gamesLazyItems[index] ?: return@items
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .clickable {
                        onGameClick.invoke(article)
                    }
                    .padding(16.dp)
                    .testTag("home_game_item_$index"),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(100.dp)
                        .height(160.dp),
                    model = article.imageUrl.backgroundImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = article.name)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun HomePreview() {
    PlayInfoTheme {
        HomeRoute({})
    }
}
