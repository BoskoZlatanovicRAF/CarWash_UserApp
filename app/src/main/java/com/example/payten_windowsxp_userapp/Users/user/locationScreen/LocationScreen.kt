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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import java.net.URLDecoder
import kotlin.math.max
import kotlin.math.min

fun NavGraphBuilder.locationScreen(
    route: String,
) {
    // Ruta za bottom navigation
    composable(route = route) {
        LocationScreen(selectedCarWash = null)
    }

    // Ruta za navigaciju iz UserHomeScreen sa parametrima
    composable(
        route = "$route/{lat}/{lon}/{name}",
        arguments = listOf(
            navArgument("lat") { type = NavType.StringType },
            navArgument("lon") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
        val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull() ?: 0.0
        val name = backStackEntry.arguments?.getString("name")?.let {
            URLDecoder.decode(it, "UTF-8")
        } ?: ""

        LocationScreen(
            selectedCarWash = CarWashLocation(lat, lon, name)
        )
    }
}

@Composable
fun LocationScreen(
    selectedCarWash: CarWashLocation? = null,
    locations: List<CarWashLocation> = listOf(
        CarWashLocation(44.837411, 20.402724, "Novi Beograd"),
        CarWashLocation(44.82414368294484, 20.39677149927324, "Stara Pazova "),
        CarWashLocation(44.800371, 20.456867, "Stari Grad"), // vidljiva perionica
        CarWashLocation(44.792307, 20.491119, "Vracar Wash"),
        CarWashLocation(44.774992, 20.476667, "Vozdovac Wash"),
        CarWashLocation(44.778358, 20.415154, "Banovo Brdo Wash")
    )
) {
    val hiddenPoint = Point(44.806530, 20.464254) // nevidljiva perionica


    var mapView by remember { mutableStateOf<MapView?>(null) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Declare MapObjectCollections
    val markerCollection = remember { mutableStateOf<MapObjectCollection?>(null) }
    val polylineCollection = remember { mutableStateOf<MapObjectCollection?>(null) }

    // Function to start location updates
    fun startLocationUpdates(locationManager: LocationManager) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                // Try to get the last known GPS location
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    currentLocation = it
                }

                // Register for GPS updates
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f
                ) { location ->
                    currentLocation = location
                }

                // If GPS is not available, use network provider
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

    // Request location permissions
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
        if (hasLocationPermission) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            startLocationUpdates(locationManager)
        }
    }

    // Check for location permissions
    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        MapKitFactory.getInstance().onStart()
    }

    // Move camera to current location
    LaunchedEffect(currentLocation) {
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

    // Iscrtaj rutu od trenutne lokacije do selektovane perionice
    LaunchedEffect(currentLocation, selectedCarWash) {
        if (currentLocation != null && selectedCarWash != null) {
            mapView?.map?.let { map ->
                polylineCollection.value?.apply {
                    // Brisanje postojece rute
                    clear()

                    val routeBetweenLiveLocation_Hidden = listOf(
                        Point(currentLocation!!.latitude, currentLocation!!.longitude),
                        hiddenPoint
                    )

                    // Draw route with your theme color
                    addPolyline(Polyline(routeBetweenLiveLocation_Hidden)).apply {
                        setStrokeColor(android.graphics.Color.argb(255, 0, 0, 255))
                        strokeWidth = 2f
                    }

                    val routeBetweenHidden_Selected = listOf(
                        hiddenPoint,
                        Point(selectedCarWash.latitude, selectedCarWash.longitude)
                    )

                    addPolyline(Polyline(routeBetweenHidden_Selected)).apply {
                        setStrokeColor(android.graphics.Color.argb(255, 0,0, 255))
                        strokeWidth = 2f
                    }

                    // Adjust camera to show both points
                    val boundingBox = BoundingBoxHelper.fromPoints(routeBetweenHidden_Selected)
                    map.move(
                        CameraPosition(boundingBox.northEast, 12.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 1.0f),
                        null
                    )
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView = this

                    markerCollection.value = map.mapObjects.addCollection()
                    polylineCollection.value = map.mapObjects.addCollection()

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
                        markerCollection.value?.addPlacemark(
                            Point(location.latitude, location.longitude)
                        )?.apply {
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
                markerCollection.value?.apply {
                    // Clear existing markers
                    clear()

                    // Add car wash markers
                    locations.forEach { carWash ->
                        addPlacemark(
                            Point(carWash.latitude, carWash.longitude)
                        ).apply {
                            setIcon(ImageProvider.fromAsset(context, "car_wash_marker.png"))
                        }
                    }

                    // Add current location marker
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
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
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
                    Icon(
                        Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "Zoom In",
                        modifier = Modifier.size(20.dp)
                    )
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
                    Icon(
                        Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Zoom Out",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()
        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }
}

// Helper object to calculate bounding box from a list of points
object BoundingBoxHelper {
    fun fromPoints(points: List<Point>): BoundingBox {
        var minLat = points.first().latitude
        var maxLat = points.first().latitude
        var minLon = points.first().longitude
        var maxLon = points.first().longitude

        for (point in points) {
            minLat = min(minLat, point.latitude)
            maxLat = max(maxLat, point.latitude)
            minLon = min(minLon, point.longitude)
            maxLon = max(maxLon, point.longitude)
        }

        return BoundingBox(
            Point(maxLat, maxLon),
            Point(minLat, minLon)
        )
    }
}
