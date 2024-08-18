package com.example.weather

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherAppUI(weatherViewModel: WeatherViewModel = viewModel()) {

    val temperature by weatherViewModel::temperature
    val date by weatherViewModel::date

    Scaffold(
        topBar = { TopBar(city = "Kolkata", date = date) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            WeatherInfoCard(
                temperature = temperature,
                weatherCondition = "Sunny",
                iconRes = R.drawable.ic_launcher_foreground
            )
            Spacer(modifier = Modifier.height(16.dp))
            AdditionalInfo(
                humidity = "65%",
                windSpeed = "15 km/h",
                pressure = "1015 hPa"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "7-Day Forecast",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ForecastList(
                forecastData = listOf(
                    "Mon" to "26°C",
                    "Tue" to "27°C",
                    "Wed" to "24°C",
                    "Thu" to "22°C",
                    "Fri" to "23°C",
                    "Sat" to "24°C",
                    "Sun" to "25°C"
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(city: String, date: String) {
    TopAppBar(
        title = {
            Column{
                Text(text = city, fontSize = 20.sp, color = Color.White)
                Text(text = date, fontSize = 14.sp, color = Color.Gray)
            }
        },
    )
}

@Composable
fun WeatherInfoCard(temperature: String, weatherCondition: String, iconRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = temperature, fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimary)
                Text(text = weatherCondition, fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Weather Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Composable
fun AdditionalInfo(humidity: String, windSpeed: String, pressure: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        InfoItem(label = "Humidity", value = humidity)
        InfoItem(label = "Wind", value = windSpeed)
        InfoItem(label = "Pressure", value = pressure)
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun ForecastList(forecastData: List<Pair<String, String>>) {
    LazyColumn {
        items(forecastData.size) { index ->
            ForecastItem(day = forecastData[index].first, temperature = forecastData[index].second)
        }
    }
}

@Composable
fun ForecastItem(day: String, temperature: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = day, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = temperature, fontSize = 16.sp)
        }
    }
}