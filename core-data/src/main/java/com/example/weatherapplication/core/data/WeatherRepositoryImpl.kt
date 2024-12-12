package com.example.weatherapplication.core.data

import com.example.weatherapplication.core.domain.WeatherRepository
import com.example.weatherapplication.core.model.WeatherResponse
import com.example.weatherapplication.core.network.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun fetchWeather(city: String): WeatherResponse {
        return api.getWeather(city, "API_KEY")
    }
}