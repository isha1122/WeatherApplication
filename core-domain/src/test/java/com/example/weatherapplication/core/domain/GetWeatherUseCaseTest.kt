package com.example.weatherapplication.core.domain

import com.example.weatherapplication.core.model.Current
import com.example.weatherapplication.core.model.Location
import com.example.weatherapplication.core.model.WeatherResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class GetWeatherUseCaseTest {
    private val mockRepository = mock(WeatherRepository::class.java)
    private val useCase = GetWeatherUseCase(mockRepository)

    @Test
    fun `invoke should fetch weather from repository`() = runBlocking {
        // Arrange
        val city = "London"
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
        `when`(mockRepository.fetchWeather(city)).thenReturn(expectedResponse)

        // Act
        val result = useCase(city)

        // Assert
        verify(mockRepository).fetchWeather(city)
        assertEquals(expectedResponse, result)
    }
}