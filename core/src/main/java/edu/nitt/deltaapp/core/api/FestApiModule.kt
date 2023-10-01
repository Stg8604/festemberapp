package edu.nitt.deltaapp.core.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import edu.nitt.deltaapp.core.FESTAPI_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/** FestApi Module */
@Module
object FestApiModule {
  const val TAG = "Login"
  @Provides
  @Singleton
  fun provideInterceptorFestApi(): HttpLoggingInterceptor =
    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Log.i(TAG, message)
      }
    })
  // Uncomment this to view the response body
//      .apply {
//      level = HttpLoggingInterceptor.Level.BODY
//    }

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
  fun provideGson(): Gson = GsonBuilder().setLenient()
    .enableComplexMapKeySerialization()
    .create()

  @Provides
  @Singleton
  fun provideRetrofit(
    okhttpClient: OkHttpClient,
    gson: Gson
  ): Retrofit = Retrofit.Builder()
    .client(okhttpClient)
    .baseUrl(FESTAPI_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

  @Provides
  @Singleton
  fun providesFestApi(retrofit: Retrofit): FestApiInterface =
    retrofit.create(FestApiInterface::class.java)
}
