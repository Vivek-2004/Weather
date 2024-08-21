package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel: ViewModel() {
    private val _weatherService = weatherservice

    var temperature by mutableStateOf("Temperature")
        private set

    var date by mutableStateOf("Date")
        private set

    var humidity by mutableStateOf("Humidity")
        private set

    var wind by mutableStateOf("Wind")
        private set

    var precipitation by mutableStateOf("Rain")
        private set

    var isDay by mutableStateOf(-1)

    private var isDataFetched = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeather(latitude : Double, longitude : Double) {
        if (isDataFetched) return

        viewModelScope.launch {
            var time = ""
            try {
                val weatherData = _weatherService.getWeather(latitude,longitude)
                temperature = weatherData.current.apparent_temperature.toString()
                time = "${weatherData.current.time}:00Z"
                humidity = weatherData.current.relative_humidity_2m.toString()
                wind = weatherData.current.wind_speed_10m.toString()
                precipitation = weatherData.current.precipitation.toString()
                isDay = weatherData.current.is_day
                isDataFetched = true
            } catch (e: Exception) {
                temperature = "Error Fetching"
                time = "1970-01-01T00:00:00Z"
            }

            // Formats the time into IST
            val timeParse = OffsetDateTime.parse(time)
            val istTime = timeParse.atZoneSameInstant(ZoneId.of("Asia/Kolkata"))
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            time = istTime.format(formatter)

            // Separates the Date and Time in the UI
            val list: List<String> = time.split(" ").toMutableList()
            date = list[0]
        }
    }
}