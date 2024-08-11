package dev.sagar.playinfo.data.repository

import dev.sagar.playinfo.domain.GameDetail
import dev.sagar.playinfo.domain.GameItem
import dev.sagar.playinfo.domain.Result

interface GameRepository {
    suspend fun getAllGames(page: Int): Result<List<GameItem>>
    suspend fun getGameDetail(gameId: Int): Result<GameDetail>
}
