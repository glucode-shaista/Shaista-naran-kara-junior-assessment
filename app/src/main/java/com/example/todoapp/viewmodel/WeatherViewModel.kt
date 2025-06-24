package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.WeatherRepository
import com.example.todoapp.network.Location
import com.example.todoapp.network.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    fun loadWeather(location: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(location)
                _weatherData.value = response
                println("weathe loaded succesfully: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                println("failed to load weather: ${e.message}")
                _weatherData.value = null
            }
        }
    }
}