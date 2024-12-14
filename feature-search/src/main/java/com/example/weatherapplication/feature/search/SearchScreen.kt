package com.example.weatherapplication.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController) {
    var city by remember { mutableStateOf("") }

    Column {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") }
        )
        Button(onClick = { navController.navigate("weather/$city") }) {
            Text("Get Weather")
        }
    }
}