package dev.sagar.playinfo.data.mapper

import dev.sagar.playinfo.core.mappers.Mapper
import dev.sagar.playinfo.data.remote.model.GameListResponse
import dev.sagar.playinfo.domain.Game
import dev.sagar.playinfo.domain.GamePlatform
import dev.sagar.playinfo.domain.Images
import javax.inject.Inject

class GameListResponseToDomainMapperImpl @Inject constructor() : Mapper<GameListResponse, List<Game>> {
    override fun convert(data: GameListResponse): List<Game> {
        val games = data.results?.map { result ->
            Game(
                id = result?.id ?: 0,
                name = result?.name ?: "",
                imageUrl = Images(
                    backgroundImage = result?.backgroundImage ?: "",
                    screenshots = result?.shortScreenshots?.map { it?.image ?: "" } ?: emptyList(),
                ),
                rating = result?.rating ?: 0.0,
                released = result?.released ?: "",
                gamePlatforms = result?.platforms?.map {
                    GamePlatform.create(
                        it?.platform?.name ?: ""
                    )
                } ?: emptyList(),
                tags = result?.tags?.map {
                    it?.name ?: ""
                } ?: emptyList(),
            )
        } ?: emptyList()
        return games
    }
}
