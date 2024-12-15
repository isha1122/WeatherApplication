package com.example.weatherapplication.core.model

/**
 * Data model representing the response from the weather API.
 */
data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val country: String,
    val region: String,
    val localtime: String
)

data class Current(
    val temperature: Int,
    val weather_descriptions: List<String>,
    val humidity: Int,
    val wind_speed: Int
)