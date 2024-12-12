package com.example.weatherapplication.core.model

/**
 * Data model representing the response from the weather API.
 */
data class WeatherResponse(
    val city: String,
    val temperature: Double,
    val description: String
)