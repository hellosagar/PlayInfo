package dev.sagar.playinfo.data.repository

import dev.sagar.playinfo.domain.Game
import dev.sagar.playinfo.domain.Result

interface GameRepository {
    suspend fun getAllGames(page: Int): Result<List<Game>>
}
