package com.example.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Address
import android.location.Geocoder
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Location(weatherViewModel: WeatherViewModel = viewModel())
{
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var latitude by remember { mutableStateOf(0.00) }
    var longitude by remember { mutableStateOf(0.00) }
    var post by remember { mutableStateOf("") }

    val requestLocationPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) {
            isGranted ->
        if(isGranted) {
            getLastLocation(context, fusedLocationClient) { lat, long ->
                latitude = lat
                longitude = long
                post = getPostalCode(context, latitude, longitude)

                weatherViewModel.fetchWeather(post)
            }
        }
    }
    LaunchedEffect(Unit) {
        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    WeatherApp(weatherViewModel)
}

fun getLastLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationRetrieved: (Double, Double) -> Unit
) {
    if (ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
    {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    onLocationRetrieved(it.latitude, it.longitude)
                }
            }
            .addOnFailureListener {
                // TODO Handle failure getting location
            }
    } else {
        // TODO Handle when location permission is not granted
    }
}

fun getPostalCode(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>?
    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                return addresses[0].postalCode ?: "-1"
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}