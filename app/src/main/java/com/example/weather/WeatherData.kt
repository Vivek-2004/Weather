package com.example.weather

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain?,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class Rain(val mm : Double?)

data class Clouds(val all: Int)

data class Coord(val lon: Double, val lat: Double)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)

data class Wind(val speed: Double, val deg: Int, val gust: Double)

data class Sys(val country: String, val sunrise: Long, val sunset: Long)

data class Main(val temp: Double, val feels_like: Double, val temp_min: Double, val temp_max: Double,
                val pressure: Int, val humidity: Int, val sea_level: Int, val grnd_level: Int)

/*

{
  "coord": {
    "lon": 87.37,
    "lat": 23.51
  },
  "weather": [
    {
      "id": 804,
      "main": "Clouds",
      "description": "overcast clouds",
      "icon": "04n"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 26.55,
    "feels_like": 26.55,
    "temp_min": 26.55,
    "temp_max": 26.55,
    "pressure": 1010,
    "humidity": 78,
    "sea_level": 1010,
    "grnd_level": 1001
  },
  "visibility": 10000,
  "wind": {
    "speed": 2.61,
    "deg": 180,
    "gust": 3.49
  },
  "clouds": {
    "all": 100
  },
  "dt": 1729090889,
  "sys": {
    "country": "IN",
    "sunrise": 1729037276,
    "sunset": 1729079031
  },
  "timezone": 19800,
  "id": 1272175,
  "name": "Durgapur",
  "cod": 200
}

 */