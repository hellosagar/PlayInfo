package dev.sagar.playinfo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.data.repository.AuthRepository
import dev.sagar.playinfo.data.repository.AuthRepositoryImpl
import dev.sagar.playinfo.data.repository.GameRepository
import dev.sagar.playinfo.data.repository.GameRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl,
    ): GameRepository
}
