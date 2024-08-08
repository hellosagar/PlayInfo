package dev.sagar.playinfo.feature.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.sagar.playinfo.data.repository.GameRepository
import dev.sagar.playinfo.domain.Game
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesPagerUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {
    fun execute(): Flow<PagingData<Game>> = Pager(
        PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = false
        )
    ) {
        GetGamesPagingSource(gameRepository)
    }.flow
}
