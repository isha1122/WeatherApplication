package com.example.weatherapplication.feature.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.verify  // Use verify from Mockk

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun searchScreen_enterCityAndNavigate() {
        // Create a mocked NavController
        val mockNavController = mockk<NavController>(relaxed = true)

        composeTestRule.setContent {
            SearchScreen(navController = mockNavController)
        }

        // Find the text field and input city name
        val cityName = "Paris"
        composeTestRule
            .onNodeWithText("Enter City")
            .performTextInput(cityName)

        // Find and click the "Get Weather" button
        composeTestRule
            .onNodeWithText("Get Weather")
            .performClick()

        // Verify navigation is triggered with the correct argument
        verify { mockNavController.navigate("weather/$cityName") }
    }
}