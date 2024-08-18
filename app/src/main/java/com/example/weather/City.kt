package com.example.weather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.Locale

fun getCityName(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>?
    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                return addresses[0].subLocality // This gives you the city name
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}