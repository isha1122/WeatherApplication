package com.example.weatherapplication.feature.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.core.model.WeatherResponse

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Weather App") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is WeatherState.Loading -> CircularProgressIndicator()
                is WeatherState.Success -> {
                    val weather = (state as WeatherState.Success).weather
                    WeatherDisplay(weather)
                }
                is WeatherState.Error -> {
                    val message = (state as WeatherState.Error).message
                    Text("Error: $message", textAlign = TextAlign.Center)
                }
                WeatherState.Idle -> {
                    Text("Enter a city to fetch weather", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun WeatherDisplay(weather: WeatherResponse) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "City: ${weather.city}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Temperature: ${weather.temperature}°C", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${weather.description}", style = MaterialTheme.typography.bodyLarge)
    }
}

//fun WeatherScreen(viewModel: WeatherViewModel) {
//    val state by viewModel.state.collectAsState()
//
//    when (state) {
//        is WeatherState.Loading -> CircularProgressIndicator()
//        is WeatherState.Success -> {
//            val weather = (state as WeatherState.Success).weather
//            Text("${weather.city}: ${weather.temperature}°C, ${weather.description}")
//        }
//        is WeatherState.Error -> {
//            val message = (state as WeatherState.Error).message
//            Text("Error: $message")
//        }
//        WeatherState.Idle -> Text("Enter a city to fetch weather.")
//    }
//}
