package com.example.weatherapplication.feature.weather

import com.example.weatherapplication.core.model.WeatherResponse

// UI State Representation
sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}