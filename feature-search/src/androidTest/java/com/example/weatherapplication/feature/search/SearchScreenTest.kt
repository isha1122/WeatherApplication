package com.example.weatherapplication.feature.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchScreenButtonClick() {
        // Mock the NavController
        val navController = mock<NavController>()

        // Set the composable content
        composeTestRule.setContent {
            // Wrap SearchScreen inside a composable block
            SearchScreen(navController = navController)
        }

        // Find the TextField and Button in the composable
        val textField = composeTestRule.onNodeWithText("Enter City")
        val button = composeTestRule.onNodeWithText("Get Weather")

        // Perform some actions
        textField.performTextInput("New York")
        button.performClick()

        // Verify if navController's navigate method was called with the correct argument
        verify(navController).navigate("weather/New York")
    }
}