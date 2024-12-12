package com.example.weatherapplication.feature.search

import android.widget.Button
import org.w3c.dom.Text

// UI: SearchScreen Composable
@Composable
fun SearchScreen(navController: NavController) {
    var city by remember { mutableStateOf("") }
    Column {
        TextField(value = city, onValueChange = { city = it }, label = { Text("Enter City") })
        Button(onClick = { navController.navigate("weather/$city") }) {
            Text("Get Weather")
        }
    }
}