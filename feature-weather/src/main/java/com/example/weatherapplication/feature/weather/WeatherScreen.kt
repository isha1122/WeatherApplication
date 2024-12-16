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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                is WeatherState.Loading -> CircularProgressIndicator(Modifier.testTag("LoadingIndicator"))
                is WeatherState.Success -> {
                    val weather = (state as WeatherState.Success).weather
                    WeatherDisplay(weather)
                }
                is WeatherState.Error -> {
                    val message = (state as WeatherState.Error).message
                    Text("Error: $message", textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("ErrorMessage"))
                }
                WeatherState.Idle -> {
                    Text("Enter a city to fetch weather",
                        modifier = Modifier.testTag("IdleMessage"),textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun WeatherDisplay(weather: WeatherResponse) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag("WeatherDisplay")) {
        Text(text = "City: ${weather?.location?.name ?: "Unknown"}", style = TextStyle(
            fontSize = 18.sp,  // Customize font size
            fontWeight = FontWeight.Bold,  // Customize font weight
            color = Color.Black,  // Customize text color
            lineHeight = 24.sp  // Customize line height
        ),modifier = Modifier.testTag("CityText") )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Temperature: ${weather?.current?.temperature ?: 0}°C", style = TextStyle(
            fontSize = 18.sp,  // Customize font size
            fontWeight = FontWeight.SemiBold,  // Customize font weight
            color = Color.Black,  // Customize text color
            lineHeight = 24.sp  // Customize line height
        ),modifier = Modifier.testTag("TemperatureText"))
       Spacer(modifier = Modifier.height(8.dp))
       Text(text = "Description: ${weather?.current?.weather_descriptions?.joinToString() ?: "N/A"}", style = TextStyle(
           fontSize = 18.sp,  // Customize font size
           fontWeight = FontWeight.ExtraBold,  // Customize font weight
           color = Color.Black,  // Customize text color
           lineHeight = 24.sp  // Customize line height
       ),
           modifier = Modifier.testTag("DescriptionText"))
    }
}
