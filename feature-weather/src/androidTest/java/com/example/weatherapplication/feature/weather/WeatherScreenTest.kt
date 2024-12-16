package com.example.weatherapplication.feature.weather

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.weatherapplication.core.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.core.domain.GetWeatherUseCase
import com.example.weatherapplication.core.domain.WeatherRepository
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun weatherScreen_displaysIdleMessageInitially() {
        val fakeViewModel = FakeWeatherViewModel(FakeGetWeatherUseCase()) // No need to pass initial state here

        composeTestRule.setContent {
            WeatherScreen(viewModel = fakeViewModel)
        }
        composeTestRule
            .onNodeWithTag("IdleMessage") // Ensure this matches the tag in your composable
            .assertIsDisplayed()
            .assertTextEquals("Enter a city to fetch weather")
    }

    @Test
    fun weatherScreen_displaysLoadingIndicator() {
        val fakeViewModel = FakeWeatherViewModel(FakeGetWeatherUseCase())

        fakeViewModel.setState(WeatherState.Loading) // Set the state to Loading

        composeTestRule.setContent {
            WeatherScreen(viewModel = fakeViewModel)
        }

        composeTestRule
            .onNodeWithTag("LoadingIndicator") // Ensure this tag exists in your composable
            .assertIsDisplayed()
    }

    @Test
    fun weatherScreen_displaysErrorMessage() {
        val fakeViewModel = FakeWeatherViewModel(FakeGetWeatherUseCase())

        // Simulate the error state
        fakeViewModel.setState(WeatherState.Error("Network Error"))

        composeTestRule.setContent {
            WeatherScreen(viewModel = fakeViewModel)
        }

        // Wait for the UI to finish rendering
        runBlocking {
            composeTestRule.awaitIdle()
        }

        // Check if the error message is displayed
        composeTestRule
            .onNodeWithTag("ErrorMessage") // Ensure the test tag exists in the composable
            .assertIsDisplayed() // Check that the message is displayed
            .assertTextEquals("Error: Network Error") // Check that the message text is correct
    }

    @Test
    fun weatherScreen_displaysWeatherData() {
        val weather = WeatherResponse(
            location = Location(name = "London", country = "UK", region = "England", localtime = "2024-12-16 12:00"),
            current = Current(
                temperature = 15,
                weather_descriptions = listOf("Sunny"),
                humidity = 70,
                wind_speed = 12
            )
        )

        val fakeViewModel = FakeWeatherViewModel(FakeGetWeatherUseCase())
        fakeViewModel.setState(WeatherState.Success(weather))

        composeTestRule.setContent {
            WeatherScreen(viewModel = fakeViewModel)
        }

        composeTestRule
            .onNodeWithTag("CityText") // Ensure this tag exists in your composable
            .assertIsDisplayed()
            .assertTextEquals("City: London")

        composeTestRule
            .onNodeWithTag("TemperatureText") // Ensure this tag exists in your composable
            .assertIsDisplayed()
            .assertTextEquals("Temperature: 15°C")

        composeTestRule
            .onNodeWithTag("DescriptionText") // Ensure this tag exists in your composable
            .assertIsDisplayed()
            .assertTextEquals("Description: Sunny")
    }

    @Test
    fun weatherScreen_updatesWeatherData() {
        val fakeViewModel = FakeWeatherViewModel(FakeGetWeatherUseCase())

        // Initially idle
        composeTestRule.setContent {
            WeatherScreen(viewModel = fakeViewModel)
        }

        composeTestRule
            .onNodeWithTag("IdleMessage") // Ensure this tag exists in your composable
            .assertIsDisplayed()

        // Update to success state
        val weather = WeatherResponse(
            location = Location(name = "Paris", country = "France", region = "Ile-de-France", localtime = "2024-12-16 12:00"),
            current = Current(
                temperature = 20,
                weather_descriptions = listOf("Cloudy"),
                humidity = 80,
                wind_speed = 10
            )
        )
        fakeViewModel.setState(WeatherState.Success(weather))

        composeTestRule
            .onNodeWithTag("CityText")
            .assertIsDisplayed()
            .assertTextEquals("City: Paris")

        composeTestRule
            .onNodeWithTag("TemperatureText")
            .assertIsDisplayed()
            .assertTextEquals("Temperature: 20°C")

        composeTestRule
            .onNodeWithTag("DescriptionText")
            .assertIsDisplayed()
            .assertTextEquals("Description: Cloudy")
    }
}

class FakeWeatherViewModel(
    getWeatherUseCase: GetWeatherUseCase
) : WeatherViewModel(getWeatherUseCase) {

    private val _fakeState = MutableStateFlow<WeatherState>(WeatherState.Idle)

    // Provide a getter for state without overriding the final property in the parent class
    override val state: StateFlow<WeatherState> = _fakeState

    // You can use this function to set the state in tests if needed
    fun setState(newState: WeatherState) {
        _fakeState.value = newState
    }
}

class FakeGetWeatherUseCase(
    private val repository: WeatherRepository = FakeWeatherRepository()
) : GetWeatherUseCase(repository) {

    override suspend fun invoke(city: String): WeatherResponse {
        // Return a fake weather response when the method is called
        return WeatherResponse(
            location = Location(name = "London", country = "UK", region = "England", localtime = "2024-12-16 12:00"),
            current = Current(
                temperature = 15,
                weather_descriptions = listOf("Sunny"),
                humidity = 70,
                wind_speed = 12
            )
        )
    }
}

class FakeWeatherRepository : WeatherRepository {

    // Implement the abstract method from WeatherRepository
    override suspend fun fetchWeather(city: String): WeatherResponse {
        return WeatherResponse(
            location = Location(
                name = city,
                country = "Fake Country",
                region = "Fake Region",
                localtime = "2024-12-16 12:00"
            ),
            current = Current(
                temperature = 20, // Example temperature
                weather_descriptions = listOf("Cloudy"), // Example weather description
                humidity = 75, // Example humidity
                wind_speed = 10 // Example wind speed
            )
        )
    }
}