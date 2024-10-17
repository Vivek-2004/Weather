package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel: ViewModel() {
    private val _weatherService = weatherservice

    var temperature by mutableStateOf("Temperature")
        private set
    var humidity by mutableStateOf("Humidity")
        private set
    var wind by mutableStateOf("Wind")
        private set
    var precipitation by mutableStateOf("Rain")
        private set
    var weatherCode by mutableIntStateOf(0)
        private set
    var description by mutableStateOf("Description")
        private set
    var sunrise by mutableStateOf("Sunrise")
        private set
    var sunset by mutableStateOf("Sunset")
        private set
    var visibility by mutableIntStateOf(0)
        private set
    var day by mutableStateOf("Day")
        private set
    var lat by mutableStateOf(-1.0)
        private set
    var lon by mutableStateOf(-1.0)
        private set
    var name by mutableStateOf("City")
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeather(post : String) {
        viewModelScope.launch {
            try {
                val weatherData = _weatherService.getWeather(post)
                updateWeatherData(weatherData)
            }
            catch (e: Exception) {
                temperature = e.toString()
            }
        }
    }

    private fun updateWeatherData(weatherData : WeatherResponse){
        temperature = weatherData.main.temp.toString()
        humidity = weatherData.main.humidity.toString()
        wind = (Math.round(weatherData.wind.speed * 3.6 * 10) / 10.0).toString()
        precipitation = weatherData.rain?.mm?.toString() ?: "-1"
        weatherCode = weatherData.weather.first().id
        description = weatherData.weather.first().main
        sunrise = dateFormatter(weatherData.sys.sunrise)
        sunset = dateFormatter(weatherData.sys.sunset)
        visibility = (weatherData.visibility / 1000).toInt()
        day = dayFormatter(weatherData.dt)
        name = weatherData.name
        lat = weatherData.coord.lat
        lon = weatherData.coord.lon
        name = weatherData.name
    }

    private fun dateFormatter(timeStamp : Long) : String {
        val date = Date(timeStamp * 1000)
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = formatter.format(date)
        return formattedTime
    }

    private fun dayFormatter(timeStamp: Long) : String{
        val instant = Instant.ofEpochSecond(timeStamp)
        val zoneId = ZoneId.of("Asia/Kolkata")
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)
        val dayOfWeek = zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        return dayOfWeek
    }
}