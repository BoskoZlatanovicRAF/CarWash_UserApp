package com.example.payten_windowsxp_userapp.Users.user.QR

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenViewModel
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular


fun NavGraphBuilder.generateQRScreen(
    route: String,
    onBackClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val generateQRViewModel: GenerateQRViewModel = hiltViewModel(navBackStackEntry)
    val state = generateQRViewModel.state.collectAsState()
    GenerateQRScreen(
        state = state.value,
        onBackClick = onBackClick
    )
}

@Composable
fun GenerateQRScreen(
    state: GenerateQRContract.GenerateQRState,
    onBackClick: () -> Unit // Akcija za zatvaranje ekrana
) {
    if (state.loading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF212121)), // Crna pozadina
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tekst iznad QR koda
            Text(
                text = "Scan QR code on terminal",
                style = poppinsBold.copy(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )

            // QR Code
            state.qrBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .size(250.dp)
                        .background(Color(0xFF333333))
                )
            } ?: Text(
                text = "QR Code nije generisan.",
                style = poppinsRegular.copy(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray
                )
            )

            // Tekst za tajmer
            Text(
                text = "Expires in 4:37",
                style = poppinsRegular.copy(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 8.dp,bottom = 32.dp)
            )

            // Dugme za zatvaranje
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .background(
                        color = Color(0xFF333333),
                        shape = MaterialTheme.shapes.large
                    )
                    .clickable { onBackClick() }
                    .height(48.dp)
                    .fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikonica "X" levo
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close Icon",
                    tint = Color(0xFFED6825),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                )

                // Tekst "Close" centriran u sredini
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Close",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}