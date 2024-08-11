package dev.sagar.playinfo.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.sagar.playinfo.data.repository.GameRepository
import dev.sagar.playinfo.domain.GameItem
import dev.sagar.playinfo.domain.Result
import javax.inject.Inject

class GetGamesPagingSource @Inject constructor(
    private val gameRepository: GameRepository,
) : PagingSource<Int, GameItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, GameItem>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItem> {
        val page: Int = params.key ?: INITIAL_PAGE_INDEX
        val size: Int = params.loadSize

        when (val gamesResult = gameRepository.getAllGames(page)) {
            is Result.Error -> {
                return LoadResult.Error(gamesResult.error)
            }

            is Result.Success -> {
                val gameItems: List<GameItem> = gamesResult.data
                val nextPageIndex: Int? = when {
                    gameItems.size == size -> page + 1
                    else -> null
                }
                val prevPageIndex = if (page == INITIAL_PAGE_INDEX) null else page - 1
                return LoadResult.Page(
                    data = gameItems,
                    prevKey = prevPageIndex,
                    nextKey = nextPageIndex,
                )
            }
        }
    }
}
