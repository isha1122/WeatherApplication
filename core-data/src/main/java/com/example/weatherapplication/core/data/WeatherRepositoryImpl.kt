package com.example.weatherapplication.core.data

import com.example.weatherapplication.core.domain.WeatherRepository
import com.example.weatherapplication.core.model.WeatherResponse
import com.example.weatherapplication.core.network.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun fetchWeather(city: String): WeatherResponse {
        println("Calling API for city: $city")
        return api.getWeather("71200ead43c5979546e49e266b5eb2d2", city).also {
            println("API Response: $it")
        }
    }
}