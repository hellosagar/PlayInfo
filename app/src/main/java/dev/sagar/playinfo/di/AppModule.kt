package dev.sagar.playinfo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.core.utils.coroutines.AppCoroutineProvider
import dev.sagar.playinfo.core.utils.coroutines.CoroutineProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

  @Provides
  @Singleton
  fun providesCoroutineProvider(
  ): CoroutineProvider = AppCoroutineProvider()
}
