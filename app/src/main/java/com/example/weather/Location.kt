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
import com.example.weather.TestView
import com.example.weather.WeatherApp
import com.example.weather.WeatherViewModel
import com.example.weather.getCityName
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Location(weatherViewModel: WeatherViewModel = viewModel())
{
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var latitude by remember { mutableStateOf(0.00) }
    var longitude by remember { mutableStateOf(0.00) }
    var city by remember { mutableStateOf("") }

    val requestLocationPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) {
            isGranted ->
        if(isGranted) {
            getLastLocation(context, fusedLocationClient) { lat, long ->
                latitude = lat
                longitude = long
                city = getCityName(context, latitude, longitude).toString()
                weatherViewModel.fetchWeather(latitude, longitude)
            }
        }
    }
    LaunchedEffect(Unit) {
        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    WeatherApp(weatherViewModel, city = city)

    //TestView(weatherViewModel, latitude, longitude, city)
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
        // TODO Handles when location permission is not granted
    }
}