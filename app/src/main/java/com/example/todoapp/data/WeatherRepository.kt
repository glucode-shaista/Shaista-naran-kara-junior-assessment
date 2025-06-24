package com.example.todoapp.data

import com.example.todoapp.network.RetrofitClient
import com.example.todoapp.network.WeatherResponse

class WeatherRepository(private val apiKey: String) {
    suspend fun getWeather(location: String): WeatherResponse {
        return RetrofitClient.apiService.getWeatherForecast(
            apiKey = apiKey,
            location = location
        )
    }
}