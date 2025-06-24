package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.navigation.AppNavGraph
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodel.TaskViewModel
import com.example.todoapp.viewmodel.WeatherViewModel
import com.example.todoapp.viewmodel.TaskViewModelFactory
import com.example.todoapp.viewmodel.WeatherViewModelFactory
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.data.WeatherRepository
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.todoapp.location.LocationService
import android.Manifest


class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        val database = TaskDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        TaskViewModelFactory (repository)
    }

    private val weatherViewModel : WeatherViewModel by viewModels {
        val weatherRepository = WeatherRepository(apiKey = "c58e5d87bdc74404a6774912252406")
        WeatherViewModelFactory(weatherRepository)
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                loadWeatherBasedOnLocation()
            } else {
                weatherViewModel.loadWeather("Johannesburg")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRequestLocationPermission()

        setContent {
            ToDoAppTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    taskViewModel = taskViewModel,
                    weatherViewModel = weatherViewModel
                )
            }
        }
    }

    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            ) {
            loadWeatherBasedOnLocation()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun loadWeatherBasedOnLocation() {
        val locationService = LocationService(this)
        locationService.getCurrentLocation {  location ->
            if (location != null) {
                val latLng = "${location.latitude}, ${location.longitude}"
                weatherViewModel.loadWeather(latLng)
            } else {
                weatherViewModel.loadWeather("Johannesburg")
            }
        }
    }
}


