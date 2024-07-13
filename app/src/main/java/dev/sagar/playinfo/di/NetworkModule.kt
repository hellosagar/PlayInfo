package dev.sagar.playinfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.core.data.remote.GameApi
import dev.sagar.playinfo.core.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  /**
   * Provides BaseUrl as string
   */
  @Singleton
  @Provides
  fun provideBaseURL(): String {
    return BASE_URL
  }
  /**
   * Provides LoggingInterceptor for api information
   */
  @Singleton
  @Provides
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
  }
  /**
   * Provides custom OkkHttp
   */
  @Singleton
  @Provides
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient().newBuilder()

    okHttpClient.callTimeout(40, TimeUnit.SECONDS)
    okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
    okHttpClient.readTimeout(40, TimeUnit.SECONDS)
    okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
    okHttpClient.addInterceptor(loggingInter`ceptor)
    okHttpClient.build()
    return okHttpClient.build()
  }
  /**
   * Provides ApiServices client for Retrofit
   */
  @Singleton
  @Provides
  fun provideRetrofitClient(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .build()
  }
  /**
   * Provides Api Service using retrofit
   */
  @Singleton
  @Provides
  fun provideRestApiService(retrofit: Retrofit): GameApi {
    return retrofit.create(GameApi::class.java)
  }

}
