package dev.sagar.playinfo.core.data.remote

import dev.sagar.playinfo.core.data.remote.model.GameListResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface GameApi {

  @GET("games")
  suspend fun getAllGames(): List<GameListResponse>

}
