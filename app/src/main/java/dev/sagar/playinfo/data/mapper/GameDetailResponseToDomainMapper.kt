package dev.sagar.playinfo.data.mapper

import dev.sagar.playinfo.core.mappers.Mapper
import dev.sagar.playinfo.data.remote.model.GameDetailResponse
import dev.sagar.playinfo.domain.GameDetail
import javax.inject.Inject

class GameDetailResponseToDomainMapper @Inject constructor() :
    Mapper<GameDetailResponse, GameDetail> {
    override fun convert(data: GameDetailResponse): GameDetail {
        return GameDetail(
            id = data.id ?: 0,
            title = data.name ?: "",
            description = data.description ?: "",
            imageUrl = data.backgroundImage ?: "",
            rating = data.rating ?: 0.0,
            released = data.released ?: "",
        )
    }
}
