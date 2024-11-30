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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current


    fun startLocationUpdates(locationManager: LocationManager) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

                // Prvo probaj da dobiješ poslednju poznatu GPS lokaciju
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    currentLocation = it
                }

                // Registruj update-ove za GPS
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f
                ) { location ->
                    currentLocation = location
                }

                // Ako GPS nije dostupan, koristi network provider
                if (currentLocation == null) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000L,
                        10f
                    ) { location ->
                        if (currentLocation == null || location.accuracy < currentLocation!!.accuracy) {
                            currentLocation = location
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("LocationScreen", "Error getting location updates: ${e.message}")
        }
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
        if (hasLocationPermission) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            startLocationUpdates(locationManager)
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        MapKitFactory.getInstance().onStart()
    }

    LaunchedEffect(currentLocation) {
        Log.d("LocationScreen", "Location update received u LaunchedEffect: ${currentLocation?.latitude}, ${currentLocation?.longitude}")
        currentLocation?.let { location ->
            mapView?.map?.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    12.0f,
                    0.0f,
                    0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0.3f),
                null
            )
        }
    }

    Log.d("LocationScreen", "Current location: ${currentLocation?.latitude} ; ${currentLocation?.longitude}")

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView = this
                    map.move(
                        CameraPosition(
                            Point(44.837411, 20.402724),
                            12.0f,
                            0.0f,
                            0.0f
                        )
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
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Update current location marker
        currentLocation?.let { location ->
            LaunchedEffect(location) {
                mapView?.map?.mapObjects?.apply {
                    clear()
                    locations.forEach { carWash ->
                        addPlacemark(
                            Point(carWash.latitude, carWash.longitude)
                        ).apply {
                            setIcon(ImageProvider.fromAsset(context, "car_wash_marker.png"))
                        }
                    }
                    addPlacemark(
                        Point(location.latitude, location.longitude)
                    ).apply {
                        setIcon(ImageProvider.fromAsset(context, "current_location_marker.png"))
                    }
                }
            }
        }

        // Zoom controls
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .size(48.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.size(24.dp)
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
                    modifier = Modifier.size(24.dp)
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