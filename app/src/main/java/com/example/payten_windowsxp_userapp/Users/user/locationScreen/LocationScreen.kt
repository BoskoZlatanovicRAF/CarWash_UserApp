package com.example.payten_windowsxp_userapp.Users.user.locationScreen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

fun NavGraphBuilder.locationScreen(
    route: String,

) = composable(
    route = route,
) {
    LocationScreen()
}


@Composable
fun LocationScreen(
    locations: List<CarWashLocation> = listOf(
        CarWashLocation(44.837411, 20.402724, "Car Wash 1"),
        CarWashLocation(44.82414368294484, 20.39677149927324, "Car Wash 2")
    )
) {
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var mapView by remember { mutableStateOf<MapView?>(null) }
    val context = LocalContext.current
    val locationManager = remember { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    var hasLocationPermission by remember { mutableStateOf(false) }
    Log.d("LocationScreen", "Current location: ${currentLocation?.latitude}, ${currentLocation?.longitude}")

    // Request location permissions
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.entries.all { it.value }
        if (hasLocationPermission) {
            // Precise location access granted
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000L,
                        10f
                    ) { location ->
                        currentLocation = location
                    }
                }
            } catch (e: SecurityException) {
                // Handle exception
                Log.e("LocationScreen", "Security Exception: ${e.message}")
            }
        }
    }

    Log.d("LocationScreen", "$hasLocationPermission")

    // Request permissions when screen launches
    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        MapKitFactory.getInstance().onStart()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView = this
                    map.move(
                        CameraPosition(
                            Point(
                                currentLocation?.latitude ?: 44.837411,
                                currentLocation?.longitude ?: 20.402724
                            ),
                            12.0f,
                            0.0f,
                            0.0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0f),
                        null
                    )

                    map.isZoomGesturesEnabled = true
                    map.isRotateGesturesEnabled = true
                    map.isScrollGesturesEnabled = true
                    map.isTiltGesturesEnabled = true

                    locations.forEach { location ->
                        map.mapObjects.addPlacemark(
                            Point(location.latitude, location.longitude)
                        ).apply {
                            setIcon(ImageProvider.fromAsset(context, "car_wash_marker.png"))
                            addTapListener { _, _ -> true }
                        }
                    }

                    // Add current location marker only if we have permission and location
                    if (hasLocationPermission && currentLocation != null) {
                        currentLocation?.let { location ->
                            try {
                                map.mapObjects.addPlacemark(
                                    Point(location.latitude, location.longitude)
                                ).apply {
                                    setIcon(ImageProvider.fromAsset(context, "current_location_marker.png"))

                                    // Add this to check if marker is set
                                    Log.d("LocationScreen", "Current location marker added at: ${location.latitude}, ${location.longitude}")
                                }
                            } catch (e: Exception) {
                                Log.e("LocationScreen", "Error adding current location marker: ${e.message}")
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Zoom buttons container
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd) // Postavlja u gornji desni ugao
                .padding(top = 16.dp, end = 16.dp) // Razmak od ivica
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .size(48.dp) // Dimenzije kvadrata
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        mapView?.map?.let { map ->
                            val currentPosition = map.cameraPosition
                            map.move(
                                CameraPosition(
                                    currentPosition.target,
                                    currentPosition.zoom + 1f,
                                    currentPosition.azimuth,
                                    currentPosition.tilt
                                ),
                                Animation(Animation.Type.SMOOTH, 0.3f),
                                null
                            )
                        }
                    },
                    modifier = Modifier.size(24.dp) // Smanjujemo dugmiće
                ) {
                    Icon(Icons.Rounded.KeyboardArrowUp, "Zoom In", modifier = Modifier.size(20.dp))
                }
                Divider(color = Color.Gray, thickness = 1.dp)
                IconButton(
                    onClick = {
                        mapView?.map?.let { map ->
                            val currentPosition = map.cameraPosition
                            map.move(
                                CameraPosition(
                                    currentPosition.target,
                                    currentPosition.zoom - 1f,
                                    currentPosition.azimuth,
                                    currentPosition.tilt
                                ),
                                Animation(Animation.Type.SMOOTH, 0.3f),
                                null
                            )
                        }
                    },
                    modifier = Modifier.size(24.dp) // Smanjujemo dugmiće
                ) {
                    Icon(Icons.Rounded.KeyboardArrowDown, "Zoom Out", modifier = Modifier.size(20.dp))
                }
            }
        }
    }




    DisposableEffect(Unit) {
        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }
}
