package dev.sagar.playinfo.data.remote

import dev.sagar.playinfo.data.remote.model.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    @GET("/games")
    suspend fun getAllGames(
        @Query("page") page: Int,
    ): GameListResponse
}
