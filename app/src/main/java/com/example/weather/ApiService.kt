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
const val hourCount: Int = 5

interface ApiService
{
    @GET("weather")
    suspend fun getWeather(
        @Query("q") post : String,
        @Query("appid") api : String = apiKey,
        @Query("units") unit: String = displayUnit
    ) : WeatherResponse

    @GET("forecast")
    suspend fun getWeekWeather(
        @Query("q") post : String,
        @Query("appid") api : String = apiKey,
        @Query("units") unit: String = displayUnit,
        @Query("cnt") count : Int = hourCount
    ) : WeekWeatherResponse
}

// https://api.openweathermap.org/data/2.5/weather?appid=e3145350b01d9ab4424ba03dd169ee28&units=metric&q=711306

// https://api.openweathermap.org/data/2.5/forecast?appid=e3145350b01d9ab4424ba03dd169ee28&units=metric&q=711303&cnt=6