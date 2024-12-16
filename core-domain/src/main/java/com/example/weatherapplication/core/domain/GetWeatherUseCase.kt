package com.example.weatherapplication.core.domain

import com.example.weatherapplication.core.model.WeatherResponse
import javax.inject.Inject

open class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    init {
        println("Repository is null: ${repository == null}")
    }
    open suspend operator fun invoke(city: String): WeatherResponse {
        return repository.fetchWeather(city)
    }
}