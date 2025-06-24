package com.example.todoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.todoapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel) {
    //location: String = "Durban"
    val weather by weatherViewModel.weatherData.collectAsState()

    //LaunchedEffect(Unit) {
    //weatherViewModel.loadWeather(location)
    //}

    weather?.let { weatherData ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFBBDEFB)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Weather",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF1E88E5),
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AsyncImage(
                        model = "https:${weatherData.current.condition.icon}",
                        contentDescription = weatherData.current.condition.text,
                        modifier = Modifier.size(48.dp)
                    )
                    Column {
                        Text(
                            text = "${weatherData.current.temp_c}°C",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = weatherData.current.condition.text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "🌄 Sunrise: ${weatherData.forecast.forecastday.first().astro.sunrise}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "🌅 Sunset: ${weatherData.forecast.forecastday.first().astro.sunset}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


