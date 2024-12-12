package com.example.weatherapplication.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.core.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Presentation Layer: ViewModel
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _state.value = WeatherState.Loading
            try {
                val weather = getWeatherUseCase(city)
                _state.value = WeatherState.Success(weather)
            } catch (e: Exception) {
                _state.value = WeatherState.Error(e.message ?: "Error fetching weather")
            }
        }
    }
}