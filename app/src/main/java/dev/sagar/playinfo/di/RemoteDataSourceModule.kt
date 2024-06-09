package dev.sagar.playinfo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.core.data.remote.FirebaseAuthDataSource
import dev.sagar.playinfo.core.data.remote.FirebaseAuthDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
  @Binds
  abstract fun bindAuthDataSource(
    firebaseAuthDataSourceImpl: FirebaseAuthDataSourceImpl,
  ): FirebaseAuthDataSource
}
