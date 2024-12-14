package com.example.weatherapplication.feature.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapplication.core.model.WeatherResponse

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
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
        Text(text = "City: ${weather.city}", style = TextStyle(
            fontSize = 18.sp,  // Customize font size
            fontWeight = FontWeight.Bold,  // Customize font weight
            color = Color.Black,  // Customize text color
            lineHeight = 24.sp  // Customize line height
        )  )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Temperature: ${weather.temperature}°C", style = MaterialTheme.typography.bodyLarge)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Description: ${weather.description}", style = MaterialTheme.typography.bodyLarge)
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
