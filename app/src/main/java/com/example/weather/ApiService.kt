package com.example.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.open-meteo.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val weatherservice = retrofit.create(ApiService::class.java)

interface ApiService
{
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
        @Query("current") current : String = "apparent_temperature"
    ) : WeatherResponse
}

/*
https://api.open-meteo.com/v1/forecast?latitude=23.51&longitude=87.37&current=relative_humidity_2m,apparent_temperature,is_day,precipitation,wind_speed_10m&wind_speed_unit=ms

{
  "latitude": 23.5,
  "longitude": 87.375,
  "generationtime_ms": 0.0640153884887695,
  "utc_offset_seconds": 0,
  "timezone": "GMT",
  "timezone_abbreviation": "GMT",
  "elevation": 80,
  "current_units": {
    "time": "iso8601",
    "interval": "seconds",
    "relative_humidity_2m": "%",
    "apparent_temperature": "°C",
    "is_day": "",
    "precipitation": "mm",
    "wind_speed_10m": "m/s"
  },
  "current": {
    "time": "2024-08-20T21:30",
    "interval": 900,
    "relative_humidity_2m": 93,
    "apparent_temperature": 33,
    "is_day": 0,
    "precipitation": 0,
    "wind_speed_10m": 1.39
  }
}
 */