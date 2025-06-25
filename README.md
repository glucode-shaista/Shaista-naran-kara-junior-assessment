To-Do App with Weather Integration
A modern, clean Kotlin Android application that combines efficient task management with real-time weather updates. Built using Jetpack Compose and follows best practices in UI design, state handling, and secure data storage.

Features
•	Add, Edit & Delete Tasks.
•	Current Weather Info (auto-detects user location).
•	Mark Tasks as Completed or Favorite.
•	Task Prioritization (High, Medium, Low).
•	Sunrise & Sunset Display.
•	Light & Dark Mode Support.
•	Secure Local Data Storage using Room DB.
•	Responsive UI designed with Material3 & Jetpack Compose.

Tech Stack
•	Language – Kotlin
•	UI Framework	 - Jetpack Compose + Material 3
•	State Management - ViewModel + StateFlows
•	Local Storage - Room Database
•	Weather API - WeatherAPI.com
•	Location Access – FusedLocationProviderClient
•	Navigation - Jetpack Navigation Compose
•	Architecture - MVVM (Model-View-ViewModel)

Getting Started
•	Prerequisites
-	Android Studio Hedgehog or newer
-	Kotlin 1.9+ and Compose BOM
-	WeatherAPI.com API key (free)

Setup Instructions
1.	Clone the repo
-	git clone https://github.com/glucode-shaista/ Shaista-naran-kara-junior-assessment.git
2.	Open in Android Studio
3.	Add your Weather API key
-	In WeatherRepository.kt, replace:
  val weatherRepository = WeatherRepository(apiKey = "YOUR_API_KEY")
4.	Run the app on your device or emulator

Permissions Used
-	ACCESS_FINE_LOCATION — to auto-fetch your current location for weather updates.
