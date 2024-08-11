package dev.sagar.playinfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.core.mappers.Mapper
import dev.sagar.playinfo.data.mapper.GameDetailResponseToDomainMapper
import dev.sagar.playinfo.data.mapper.GameListResponseToDomainMapperImpl
import dev.sagar.playinfo.data.remote.model.GameDetailResponse
import dev.sagar.playinfo.data.remote.model.GameListResponse
import dev.sagar.playinfo.domain.GameDetail
import dev.sagar.playinfo.domain.GameItem

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun provideGameListResponseToDomainMapper(): Mapper<GameListResponse, List<GameItem>> =
        GameListResponseToDomainMapperImpl()

    @Provides
    fun provideGameDetailResponseToDomainMapper(): Mapper<GameDetailResponse, GameDetail> =
        GameDetailResponseToDomainMapper()
}
