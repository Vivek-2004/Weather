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
    https://api.open-meteo.com/v1/forecast?latitude=23.51&longitude=87.37&current=apparent_temperature

{
  "latitude": 23.5,
  "longitude": 87.375,
  "generationtime_ms": 0.0389814376831055,
  "utc_offset_seconds": 0,
  "timezone": "GMT",
  "timezone_abbreviation": "GMT",
  "elevation": 80,
  "current_units": {
    "time": "iso8601",
    "interval": "seconds",
    "apparent_temperature": "°C"
  },
  "current": {
    "time": "2024-07-29T16:45",
    "interval": 900,
    "apparent_temperature": 35.2
  }
}
 */