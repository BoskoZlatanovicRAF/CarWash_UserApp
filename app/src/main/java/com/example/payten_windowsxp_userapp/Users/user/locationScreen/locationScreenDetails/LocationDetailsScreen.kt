package com.example.payten_windowsxp_userapp.Users.user.locationScreen.locationScreenDetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular

fun NavGraphBuilder.locationDetailsScreen(
    route: String,
    onBackClick: () -> Unit
) = composable(route = route) {
    LocationDetailsScreen(onBackClick = onBackClick)
}


@Composable
fun LocationDetailsScreen(
    onBackClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
            .padding(16.dp)
    ) {
        // Top Bar with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFED6825)
                )
            }
            Text(
                text = "Location Details",
                style = poppinsBold.copy(fontSize = 24.sp),
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Image
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.car_wash_station_image),
                contentDescription = "Car Wash Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title and Location
        Text(
            text = "Stari Grad - Car Wash",
            style = poppinsBold.copy(fontSize = 24.sp),
            color = Color(0xFFED6825)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Belgrade, Serbia",
                style = poppinsRegular.copy(fontSize = 16.sp),
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Token Price Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Token price",
                    style = poppinsBold.copy(fontSize = 18.sp),
                    color = Color(0xFFED6825)
                )
                Spacer(modifier = Modifier.height(16.dp))
                PriceRow("12:00 - 18:00", "1.0€")
                PriceRow("18:00 - 00:00", "0.9€")
                PriceRow("00:00 - 06:00", "0.6€")
                PriceRow("06:00 - 12:00", "0.9€")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Live Camera Button
        Button(
            onClick = { /* Handle live camera click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isHovered = true
                            tryAwaitRelease()
                            isHovered = false
                        }
                    )
                }
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera_signal),
                    contentDescription = "Live Camera",
                    tint = Color(0xFFED6825),
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Live camera",
                    style = poppinsBold.copy(
                        fontSize = 24.sp,
                        color = if (isHovered) Color.Yellow else Color.White
                    ),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun PriceRow(
    timeRange: String,
    price: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically // Align all elements vertically centered
    ) {
        Text(
            text = timeRange,
            style = poppinsRegular.copy(fontSize = 16.sp),
            color = Color.White
        )

        // Add a spacer to separate time and price
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .align(Alignment.CenterVertically) // Align the line with the center of the row
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    color = Color(0xFFED6825),
                    start = Offset(0f, size.height / 2), // Ensure the line is vertically centered
                    end = Offset(size.width, size.height / 2), // Ensure the line is horizontally drawn
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
        }

        // Add a spacer between the line and the price
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = price,
            style = poppinsBold.copy(fontSize = 16.sp),
            color = Color.White
        )
    }
}

