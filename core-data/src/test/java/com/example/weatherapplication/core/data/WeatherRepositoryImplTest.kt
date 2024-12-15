package com.example.weatherapplication.core.data

import com.example.weatherapplication.core.model.Current
import com.example.weatherapplication.core.model.Location
import com.example.weatherapplication.core.model.WeatherResponse
import com.example.weatherapplication.core.network.WeatherApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class WeatherRepositoryImplTest {
    private val mockApi = mock(WeatherApi::class.java)
    private val repository = WeatherRepositoryImpl(mockApi)

    @Test
    fun `fetchWeather should call api and return WeatherResponse`() = runBlocking {
        // Arrange
        val city = "London"
        val apiKey = "71200ead43c5979546e49e266b5eb2d2"
        val expectedResponse = WeatherResponse(
            location = Location(
                name = "London",
                country = "United Kingdom",
                region = "England",
                localtime = "2024-12-16 14:00"
            ),
            current = Current(
                temperature = 15,
                weather_descriptions = listOf("Partly cloudy"),
                humidity = 70,
                wind_speed = 10
            )
        )
        `when`(mockApi.getWeather(apiKey, city)).thenReturn(expectedResponse)

        // Act
        val result = repository.fetchWeather(city)

        // Assert
        verify(mockApi).getWeather(apiKey, city)
        assertEquals(expectedResponse, result)
    }
}