package dev.sagar.playinfo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sagar.playinfo.BuildConfig
import dev.sagar.playinfo.core.utils.Constants.BASE_URL
import dev.sagar.playinfo.core.utils.Constants.NETWORK_TIMEOUT_SEC
import dev.sagar.playinfo.data.remote.GameApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
     * Provides Interceptor for adding key parameter and appending path
     */
    @Singleton
    @Provides
    fun provideKeyAndPathInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val urlWithKey =
                originalHttpUrl.newBuilder().addQueryParameter("key", BuildConfig.API_KEY).build()

            val urlWithKeyAndPath =
                urlWithKey.newBuilder().encodedPath("/api" + urlWithKey.encodedPath).build()

            val requestBuilder = originalRequest.newBuilder().url(urlWithKeyAndPath)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * Provides Moshi adapter
     */
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    /**
     * Provides Moshi Convertor factory
     */
    @Singleton
    @Provides
    fun provideConvertorFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        apiInterceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        okHttpClient.readTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(apiInterceptor)
        okHttpClient.addInterceptor(ChuckerInterceptor(context))
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
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(converterFactory).build()
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
