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
fun View(weatherViewModel: WeatherViewModel = viewModel(), latitude : Double, longitude : Double)
{
    // THIS METHOD IS MORE STRAIGHTFORWARD AND THE UI GETS UPDATED WHENEVER THESE IS A CHANGE IN THE weatherViewModel()
    val temperature by weatherViewModel::temperature
    val date by weatherViewModel::date

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text( text = "Date : $date", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Temperature : $temperature", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Latitude : $latitude", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text( text = "Longitude : $longitude", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}