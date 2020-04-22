package edu.nitt.delta.core.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/** FestApi Module */
@Module
object FestApiModule {
  const val TAG = "FestAPI"

  @Provides
  @Singleton
  fun provideInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Log.i(TAG, message)
      }
    })

  @Provides
  @Singleton
  fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .callTimeout(10, TimeUnit.SECONDS)
      .readTimeout(5, TimeUnit.SECONDS)
      .writeTimeout(5, TimeUnit.SECONDS)
      .build()

  @Provides
  @Singleton
  fun provideGson(): Gson = GsonBuilder()
    .enableComplexMapKeySerialization()
    .create()

  @Provides
  @Singleton
  fun provideRetrofit(
    okhttpClient: OkHttpClient,
    gson: Gson,
    @Named("url") url: String
  ): Retrofit = Retrofit.Builder()
    .client(okhttpClient)
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

  @Provides
  @Singleton
  fun providesFestApi(retrofit: Retrofit): FestApiInterface =
    retrofit.create(FestApiInterface::class.java)
}
