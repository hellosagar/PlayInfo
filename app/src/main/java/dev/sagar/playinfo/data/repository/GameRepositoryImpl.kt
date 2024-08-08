package dev.sagar.playinfo.data.repository

import dev.sagar.playinfo.core.errorhandling.SafeApiCall
import dev.sagar.playinfo.core.mappers.Mapper
import dev.sagar.playinfo.data.remote.GameApi
import dev.sagar.playinfo.data.remote.model.GameListResponse
import dev.sagar.playinfo.domain.Game
import dev.sagar.playinfo.domain.Result
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val safeApiCall: SafeApiCall,
    private val gameApi: GameApi,
    // Using the @JvmSuppressWildcards due to the issue - https://github.com/google/dagger/issues/1607#issue-490732878
    private val gamesToDomainMapper: @JvmSuppressWildcards Mapper<GameListResponse, List<Game>>,
) : GameRepository {
    override suspend fun getAllGames(page: Int): Result<List<Game>> =
        safeApiCall.invoke {
            gamesToDomainMapper.convert(gameApi.getAllGames(page))
        }
}
