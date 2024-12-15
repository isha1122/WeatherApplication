package com.example.weatherapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapplication.feature.search.SearchScreen
import com.example.weatherapplication.feature.weather.WeatherScreen
import com.example.weatherapplication.feature.weather.WeatherViewModel


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        composable("search") {
            SearchScreen(navController)
        }
        composable("weather/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            val viewModel: WeatherViewModel = hiltViewModel()
            WeatherScreen(viewModel)
            LaunchedEffect(city) {
                viewModel.fetchWeather(city)
            }
        }
    }
}