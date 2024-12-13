package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.weather.ui.theme.WeatherTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = viewModel()) {

    val temp by weatherViewModel::temperature
    val wind by weatherViewModel::wind
    val humidity by weatherViewModel::humidity
    val precipitation by weatherViewModel::precipitation
    val description by weatherViewModel::description
    val sunrise by weatherViewModel::sunrise
    val sunset by weatherViewModel::sunset
    val visibility by weatherViewModel::visibility
    val day by weatherViewModel::day
    val city by weatherViewModel::name
    val iconURL by weatherViewModel::iconUrl

    WeatherTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.A),
                        colorResource(R.color.B),
                        colorResource(R.color.C),
                        colorResource(R.color.D),
                        colorResource(R.color.E)
                    )
                )
            )
        ) {

            TopBarWithSearch()

            TodayWeatherCard(
                temperature = temp,
                weatherDescription = description,
                location = city,
                day = day,
                imageURL = iconURL
            )

            TodayWeatherDetailsSection(
                windSpeed = wind,
                humidity = humidity,
                sunrise = sunrise,
                sunset = sunset,
                precipitation = precipitation,
                visibility = visibility.toString()
            )

            HourlyWeatherRow()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(weatherViewModel: WeatherViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    val onSearch: (WeatherViewModel, String) -> Unit = { weatherViewModel, post ->
        weatherViewModel.fetchWeather(post)
    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 5.dp, end = 16.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        ),
        title = {
            TextField(
                value = searchQuery,
                onValueChange = { newQuery -> searchQuery = newQuery },
                placeholder = { Text("Enter Location...", color = Color.White.copy(alpha = 0.7f)) },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                trailingIcon = if (searchQuery.isNotEmpty()) {
                    {
                        IconButton(onClick = { onSearch(weatherViewModel, searchQuery) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_enter),
                                contentDescription = "Enter",
                                tint = Color.White
                            )
                        }
                    }
                } else null,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.3f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                    disabledContainerColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    disabledTextColor = Color.White.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TodayWeatherCard(temperature: String, weatherDescription: String, location: String, day: String, imageURL : String) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.E)),
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
            Text(
                text = location,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = day,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$temperature °C",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = Color.Blue
                    )
                )
                Spacer(modifier = Modifier.width(14.dp))
                Image(
                    painter = rememberImagePainter(data = imageURL),
                    contentDescription = "Sunny Icon",
                    modifier = Modifier.size(80.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherDescription,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun TodayWeatherDetailsSection(
    windSpeed: String,
    humidity: String,
    sunrise: String,
    sunset: String,
    precipitation: String,
    visibility: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.E)),
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(label = "Wind", value = "$windSpeed km/h")
                WeatherDetailItem(label = "Humidity", value = "$humidity %")
            }
            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(label = "Sunrise", value = sunrise)
                Column(modifier = Modifier.wrapContentHeight().width(145.dp)) {
                    Icon( modifier = Modifier.wrapContentSize(),
                        painter = painterResource(id = R.drawable.sunrise_sunset),
                        contentDescription = "Sunrise and Sunset Icon",
                        tint = colorResource(R.color.sunrise_sunset),
                    )
                }
                WeatherDetailItem(label = "Sunset", value = sunset)
            }
            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(label = "Precipitation", value = "$precipitation mm")
                WeatherDetailItem(label = "Visibility", value = "$visibility km")
            }
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Blue
            )
        )
    }
}

@Composable
fun HourlyWeatherRow() {
    LazyRow( modifier = Modifier.padding(8.dp) )
    {
        items(8){
            HourlyWeatherCard()
        }
    }
}

@Composable
fun HourlyWeatherCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.height(190.dp).padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Text(
                text = "03:00 pm",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Blue
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = "28.0 °C",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Blue
                )
            )
            Text(
                text = "4.5 km/h ↗",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Blue
                )
            )
        }
    }
}