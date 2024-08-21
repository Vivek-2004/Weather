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
    val relative_humidity_2m : String,
    val apparent_temperature : String,
    val is_day : String,
    val precipitation : String,
    val wind_speed_10m : String
)

data class Current(
    val time : String,
    val interval : Int,
    val relative_humidity_2m : Int,
    val apparent_temperature : Double,
    val is_day : Int,
    val precipitation : Int,
    val wind_speed_10m : Double
)

/*
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
    "apparent_temperature": 33.2,
    "is_day": 0,
    "precipitation": 0,
    "wind_speed_10m": 1.39
  }
}

// Old Api Used in the app
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