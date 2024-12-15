package com.example.weatherapplication.feature.weather

import com.example.weatherapplication.core.domain.GetWeatherUseCase
import com.example.weatherapplication.core.model.WeatherResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.whenever
import com.example.weatherapplication.core.model.Current
import com.example.weatherapplication.core.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    // Create a test dispatcher for coroutines
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: WeatherViewModel
    private val getWeatherUseCase: GetWeatherUseCase = mock()

    @Before
    fun setup() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel with mocked use case
        viewModel = WeatherViewModel(getWeatherUseCase)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test fetchWeather calls the use case`() = runBlocking {
        // Prepare mock data
        val mockWeather = WeatherResponse(
            location = Location("New York", "USA", "NY", "2024-12-14"),
            current = Current(25, listOf("Sunny"), 50, 10)
        )

        // Stubbing the use case
        whenever(getWeatherUseCase("New York")).thenReturn(mockWeather)

        // Run the ViewModel method
        viewModel.fetchWeather("New York")

        // Verify that the use case was called with the expected argument
        verify(getWeatherUseCase).invoke("New York")

        // Collect the state to check the state changes
        val state = viewModel.state.first()

        // Assert the expected state
        assert(state is WeatherState.Success)
        val successState = state as WeatherState.Success
        assert(successState.weather == mockWeather)
    }

    @Test
    fun `test fetchWeather handles error correctly`() = runBlocking {
        // Prepare mock error
        val errorMessage = "Error fetching weather"
        whenever(getWeatherUseCase("New York")).thenThrow(RuntimeException(errorMessage))

        // Run the ViewModel method
        viewModel.fetchWeather("New York")

        // Collect the state to check the state changes
        val state = viewModel.state.first()

        // Assert the expected state
        assert(state is WeatherState.Error)
        val errorState = state as WeatherState.Error
        assert(errorState.message == errorMessage)
    }
}