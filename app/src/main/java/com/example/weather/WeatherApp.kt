package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    WeatherTheme {
        Column( modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    colorResource(R.color.A),
                    colorResource(R.color.B),
                    colorResource(R.color.C),
                    colorResource(R.color.D),
                    colorResource(R.color.E)
                )))
        ) {

            TopBarWithSearch()

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                item {
                    TodayWeatherCard(
                        temperature = temp,
                        weatherDescription = description,
                        location = city,
                        day = day
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    TodayWeatherDetailsSection(
                        windSpeed = wind,
                        humidity = humidity,
                        sunrise = sunrise,
                        sunset = sunset,
                        precipitation = precipitation,
                        visibility = visibility.toString()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    WeekWeatherSection()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(weatherViewModel: WeatherViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    val onSearch: (WeatherViewModel, String) -> Unit = { weatherViewModel, post ->
        run {
            weatherViewModel.fetchWeather(post)
        }
    }

    TopAppBar(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 28.dp),
        colors = TopAppBarDefaults.topAppBarColors( containerColor = Color.Transparent, titleContentColor = Color.White ),
        title = {
            TextField( value = searchQuery, onValueChange = { newQuery -> searchQuery = newQuery },
                placeholder = { Text("Enter Location...", color = Color.White.copy(alpha = 0.7f) ) },
                singleLine = true,
                leadingIcon = { Icon( painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = Color.White )
                },
                trailingIcon = if (searchQuery.isNotEmpty()) {
                    {
                        IconButton(onClick = { onSearch(weatherViewModel, searchQuery) }) {
                            Icon( painter = painterResource(id = R.drawable.arrow_enter),
                                contentDescription = "Enter",
                                tint = Color.White )
                        }
                    }
                } else null,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    containerColor = Color.White.copy(alpha = 0.3f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun TodayWeatherCard( temperature: String, weatherDescription: String, location: String, day: String) {
    Card( shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.E)),
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = Modifier.fillMaxWidth().shadow(12.dp, RoundedCornerShape(24.dp)).padding(8.dp).clip(RoundedCornerShape(24.dp))
    ) {
        Column( modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Text( text = location,
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
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = Color.Blue
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = "Sunny Icon",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(48.dp)
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
    sunrise : String,
    sunset : String,
    precipitation : String,
    visibility : String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.E)),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly )
            {

                WeatherDetailItem( label = "Wind", value = "$windSpeed km/h" )
                WeatherDetailItem( label = "Humidity", value = "$humidity %")
            }
            Spacer(Modifier.height(24.dp))

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween )
            {
                WeatherDetailItem( label = "Sunrise", value = sunrise )

                Icon(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = "Weather Icon",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(40.dp)
                )

                WeatherDetailItem( label = "Sunset", value = sunset )
            }
            Spacer(Modifier.height(24.dp))

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly )
            {
                WeatherDetailItem( label = "Precipitation", value = "$precipitation mm" )
                WeatherDetailItem( label = "Visibility", value = "$visibility km" )
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
fun WeekWeatherSection() {
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        items(7) { index ->
            WeekWeatherCard(
                day = "Day ${index + 1}",
                temperature = "${20 + index}°C",
                weatherDescription = if (index % 2 == 0) "Sunny" else "Cloudy",
                windSpeed = "${10 + index} km/h",
                humidity = "${60 + index}%"
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun WeekWeatherCard(
    day: String,
    temperature: String,
    weatherDescription: String,
    windSpeed: String,
    humidity: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.E)),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .width(200.dp)
            .shadow(12.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(colorResource(R.color.E), colorResource(R.color.E))
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = day,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.Blue
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = "Weather Icon",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherDescription,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Wind: $windSpeed",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Blue
                    )
                )
                Text(
                    text = "Humidity: $humidity",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Blue
                    )
                )
            }
        }
    }
}