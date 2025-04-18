package com.cyb.payten_windowsxp_terminalapp.washingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.payten_windowsxp_terminalapp.ui.theme.poppinsBold


fun NavGraphBuilder.washingScreens(
    route: String,
    onUserClick: (String) -> Unit
) = composable(
    route = route
) {

    val washingScreenViewModel: WashingScreenViewModel = hiltViewModel<WashingScreenViewModel>()
    val state = washingScreenViewModel.state.collectAsState()

    ThankYouScreen(
        state = state.value,
        eventPublisher = {
            washingScreenViewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )

}

@Composable
fun ThankYouScreen(
    state: WashingScreenContract.WashingScreenUiState,
    eventPublisher: (uiEvent: WashingScreenContract.WashingScreenUiEvent) -> Unit,
    onUserClick: (String) -> Unit
) {

    if(state.switchToSplash) {
        onUserClick("")
    }

    if (!state.startWashing) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    eventPublisher(
                        WashingScreenContract.WashingScreenUiEvent.StartWashingChange(true)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Increased width to 90% of the screen
                    .height(350.dp), // Increased height for a larger button
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "START\nWASHING",
                    style = poppinsBold.copy( // Using Poppins Bold font
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp, // Larger font size
                        color = Color.White,
                        lineHeight = 50.sp // Increased line spacing for better visibility
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else if (state.startWashing && !state.readyForThanks) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center, // Center everything vertically
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Remaining Time Section
            Text(
                text = "REMAINING",
                style = poppinsBold.copy( // Using Poppins Bold font
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp, // Larger font size
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "TIME",
                style = poppinsBold.copy( // Using Poppins Bold font
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp, // Larger font size
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 32.dp) // Added space between TIME and timer
            )

            Text(
                text = formatTime(state.time), // Replace with dynamic time if needed
                style = poppinsBold.copy( // Using Poppins Bold font
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 64.sp, // Extra large font size
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 64.dp) // Space between timer and button
            )

            // End Washing Button
            Button(
                onClick = {
                    eventPublisher(
                        WashingScreenContract.WashingScreenUiEvent.SwitchToThanks(true)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Increased button width
                    .height(250.dp), // Increased button height
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "END\nWASHING",
                    style = poppinsBold.copy( // Using Poppins Bold font
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp, // Larger font size
                        color = Color.White,
                        lineHeight = 50.sp // Increased line spacing for better visibility
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else if (state.readyForThanks) {
        eventPublisher(WashingScreenContract.WashingScreenUiEvent.SwitchToSplash(true))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "THANK YOU FOR\nUSING OUR CAR\nWASH!",
                style = poppinsBold.copy( // Using Poppins Bold font
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp, // Increased font size
                    color = Color.Black,
                    lineHeight = 65.sp, // Adjusted line spacing
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}