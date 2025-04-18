package com.cyb.payten_windowsxp_terminalapp.failScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.payten_windowsxp_terminalapp.R
import com.cyb.payten_windowsxp_terminalapp.washingScreen.ThankYouScreen
import com.cyb.payten_windowsxp_terminalapp.washingScreen.WashingScreenContract
import com.cyb.payten_windowsxp_terminalapp.washingScreen.WashingScreenViewModel
import java.util.concurrent.SubmissionPublisher

fun NavGraphBuilder.failScreen(
    route: String,
    onTryAgainClick: (String) -> Unit
) = composable(
    route = route
) {

    val failScreenViewModel: FailScreenViewModel = hiltViewModel<FailScreenViewModel>()
    val state = failScreenViewModel.state.collectAsState()

    FailScreen(
        state = state.value,
        eventPublisher = {
            failScreenViewModel.setEvent(it)
        },
        onTryAgainClick = onTryAgainClick
    )

}

@Composable
fun FailScreen(
    state: FailScreenContract.FailScreenState,
    eventPublisher: (uiEvent: FailScreenContract.FailScreenUiEvent) -> Unit,
    onTryAgainClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red), // Crvena pozadina
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                 // Postavi visinu kartice
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween, // Rasporedi komponente ravnomerno
                modifier = Modifier
                    .background(Color.White) // Bela pozadina kartice
                    .padding(32.dp)
            ) {
                // Crveni krug sa ikonicom "X"
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24), // Dodaj resurs ikone "X"
                        contentDescription = "Failed",
                        tint = Color.White,
                        modifier = Modifier.size(90.dp)
                    )
                }

                // Tekst "Payment Failed"
                Text(
                    text = "Payment Failed",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center
                )

                // Tekst "Try Again"
                Text(
                    text = "Try Again",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .clickable {
                        // Tvoja akcija ovde, na primer:
                            eventPublisher(FailScreenContract.FailScreenUiEvent.TryAgainClickedEvent(""))
                        onTryAgainClick("")
                        }
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,

                )
            }
        }
    }
}