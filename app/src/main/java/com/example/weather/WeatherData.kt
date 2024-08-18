package com.example.weather

data class WeatherResponse(
    val latitude : Double,
    val longitude : Double,
    val generationtime_ms : Double,
    val utc_offset_seconds : Int,
    val timezone : String,
    val timezone_abbreviation : String,
    val elevation : Int,
    val current_units : CurrentUnits,
    val current : Current
)

data class CurrentUnits(
    val time : String,
    val interval : String,
    val apparent_temperature : String
)

data class Current(
    val time : String,
    val interval : Int,
    val apparent_temperature : Double
)

/*
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