package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
// This Composable is Created to Test whether all the API requests fetches correctly.
fun TestView(weatherViewModel: WeatherViewModel = viewModel(), latitude : Double, longitude : Double, city : String)
{
    // THIS METHOD IS MORE STRAIGHTFORWARD AND THE UI GETS UPDATED WHENEVER THESE IS A CHANGE IN THE weatherViewModel()
    val temperature by weatherViewModel::temperature
    val date by weatherViewModel::date
    val humidity by weatherViewModel::humidity

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text( text = "Date : $date", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Temperature : $temperature", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Latitude : $latitude", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Longitude : $longitude", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "City : $city", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Humidity : $humidity %", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}