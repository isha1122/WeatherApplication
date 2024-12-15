package com.example.weatherapplication.core.network

import com.example.weatherapplication.core.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current")
    suspend fun getWeather(
        @Query("access_key") accessKey: String,
        @Query("query") location: String
    ): WeatherResponse
}