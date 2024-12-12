package com.example.weatherapplication.core.domain

import com.example.weatherapplication.core.model.WeatherResponse

interface WeatherRepository {
    suspend fun fetchWeather(city: String): WeatherResponse
}