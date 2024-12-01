package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

import android.Manifest
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.locationScreen.CarWashLocation
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

fun NavGraphBuilder.userHomeScreen(
    route: String,
    onBonusClick: () -> Unit,
    onCarWashClick: (CarWashLocation) -> Unit  // Dodajemo novi callback
) = composable(
    route = route,
) { navBackStackEntry ->
    val userHomeScreenViewModel: UserHomeScreenViewModel = hiltViewModel(navBackStackEntry)
    val state = userHomeScreenViewModel.state.collectAsState()
    UserHomeScreen(
        state = state.value,
        eventPublisher = {
            userHomeScreenViewModel.setEvent(it)
        },
        onBonusClick = onBonusClick,
        onCarWashClick = onCarWashClick  // Prosleđujemo callback
    )
}


@Composable
fun UserHomeScreen(
    state: UserHomeScreenContract.UserHomeScreenState,
    eventPublisher: (uiEvent: UserHomeScreenContract.UserHomeScreenEvent) -> Unit,
    onBonusClick: () -> Unit,
    onCarWashClick: (CarWashLocation) -> Unit,
) {
    val context = LocalContext.current

    var hasLocationPermission by remember { mutableStateOf(false) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Request location permissions
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // Get the current location and send updates to ViewModel
    DisposableEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                val locationRequest = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY, 5000L
                ).build()

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location = locationResult.lastLocation
                        location?.let {
                            eventPublisher(UserHomeScreenContract.UserHomeScreenEvent.UpdateCurrentLocation(location.latitude, location.longitude))
                        }
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )

                onDispose {
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                }

            } catch (e: SecurityException) {
                Log.e("UserHomeScreen", "Error getting location updates: ${e.message}")
            }
        } else {
            // Handle the case when permissions are not granted
            // Optionally, you can inform the user that location permissions are required
        }

        // Return a DisposableEffectResult
        onDispose { /* No additional cleanup */ }
    }

    if (state.loading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        Scaffold(
            containerColor = Color(0xFF212121) // Background color of the Scaffold
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFED6825),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Welcome, ${state.name}!",
                        style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleLarge.fontSize),
                        color = Color.White
                    )
                    Text(
                        text = "Gold member",
                        style = poppinsMedium.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF333333),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clickable { onBonusClick() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Bonus points",
                            style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                            color = Color.White
                        )
                        Text(
                            text = "${state.bonusPoints}",
                            style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleLarge.fontSize),
                            color = Color.White
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        tint = Color.White
                    )
                }

                // NAJBLIZA PERIONICA
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF333333), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                        .clickable {
                            state.nearestCarWash?.let { carWash ->
                                onCarWashClick(carWash)
                            }
                        }
                ) {
//                    Log.d("UserHomeScreen", "Nearest car wash: ${state.nearestCarWash}")
                    state.nearestCarWash?.let { carWash ->
//                        Log.d("UserHomeScreen", "Nearest car wash: ${carWash.name}")
                        Text(
                            text = "Nearest car wash station",
                            style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                            color = Color.White
                        )

                        Text(
                            text = carWash.name,
                            style = poppinsBold.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                            color = Color.White
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_car2_icon),
                                contentDescription = "Car Icon",
                                tint = Color(0xFFED6825),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${(state.distanceToNearestCarWash / 1000).format(1)}km, ${(state.distanceToNearestCarWash / 1000 * 2).toInt()}min",
                                style = poppinsRegular.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                                color = Color.Gray
                            )
                        }
                    } ?: run {
                        Text(
                            text = "Fetching nearest car wash...",
                            style = poppinsRegular.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                            color = Color.White
                        )
                    }
                }

                // Rest of your UI components (Recent Transactions, etc.)
                Text(
                    text = "Recent Transactions",
                    style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(state.transactions) { transaction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFF333333),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = transaction.description,
                                    style = poppinsMedium.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                                    color = Color.White
                                )
                                Text(
                                    text = transaction.date,
                                    style = poppinsRegular.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                                    color = Color.Gray
                                )
                            }

                            Text(
                                text = "+${transaction.bonusPoints} pts",
                                style = poppinsMedium.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                                color = Color(0xFFED6825),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(0.1f))
            }
        }
    }
}

// Extension function for formatting numbers
fun Float.format(digits: Int) = "%.${digits}f".format(this)
