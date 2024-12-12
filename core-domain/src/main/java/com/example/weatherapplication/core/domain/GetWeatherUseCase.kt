package com.example.weatherapplication.core.domain

import com.example.weatherapplication.core.model.WeatherResponse
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherResponse {
        return repository.fetchWeather(city)
    }
}