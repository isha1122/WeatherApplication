package com.example.weatherapplication.core.data

import com.example.weatherapplication.core.domain.WeatherRepository
import com.example.weatherapplication.core.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Hilt Module for Providing Dependencies
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
   const val addOnString="forecast/daily?APPID=71200ead43c5979546e49e266b5eb2d2"
   //const val BASE_URL = "https://api.openweathermap.org/data/2.5"+ addOnString
   private const val BASE_URL = "https://api.weatherstack.com/"
   // http://api.openweathermap.org/data/2.5/forecast/daily?APPID=12345&q=...
//http://api.weatherstack.com/current
//    ? access_key = 71200ead43c5979546e49e266b5eb2d2
//    & query = New York
}