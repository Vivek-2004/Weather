package com.example.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val weatherservice : ApiService = retrofit.create(ApiService::class.java)

const val apiKey : String = "e3145350b01d9ab4424ba03dd169ee28"
const val displayUnit : String = "metric"

interface ApiService
{
    @GET("weather")
    suspend fun getWeather(
        @Query("q") post : String,
        @Query("appid") api : String = apiKey,
        @Query("units") unit: String = displayUnit
    ) : WeatherResponse
}

// https://api.openweathermap.org/data/2.5/weather?appid=2c26c602e0cb0f7448c8906bede1ee07&units=metric&q=711303