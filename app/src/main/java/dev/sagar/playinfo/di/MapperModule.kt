package dev.sagar.playinfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.core.mappers.Mapper
import dev.sagar.playinfo.data.mapper.GameListResponseToDomainMapperImpl
import dev.sagar.playinfo.data.remote.model.GameListResponse
import dev.sagar.playinfo.domain.Game

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    /**
     * Provide initial screen mapper
     */
    @Provides
    fun provide(): Mapper<GameListResponse, List<Game>> = GameListResponseToDomainMapperImpl()
}
