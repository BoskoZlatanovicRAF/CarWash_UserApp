package com.cyb.payten_windowsxp_terminalapp.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.payten_windowsxp_terminalapp.R
import com.cyb.payten_windowsxp_terminalapp.ui.theme.poppinsBold
import com.cyb.payten_windowsxp_terminalapp.ui.theme.poppinsMedium

fun NavGraphBuilder.payment(
    route: String,
    onUserClick: (String) -> Unit
) = composable(
    route = route,
) {
    val paymentViewModel: PaymentViewModel = hiltViewModel<PaymentViewModel>()
    val state = paymentViewModel.state.collectAsState()

    PaymentScreen(
        state = state.value,
        eventPublisher = {
            paymentViewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )
}

@Composable
fun PaymentScreen(
    state: PaymentContract.PaymentContractUiState,
    eventPublisher: (uiEvent: PaymentContract.PaymentContactUiEvent) -> Unit,
    onUserClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Welcome Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (state.username.isNotBlank()) "Welcome, ${state.username}!" else "Welcome!",
                style = poppinsBold.copy( // Using custom font for bold text
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize.times(1.3f),
                    color = Color(0xFFED6825)
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = state.membership,
                style = poppinsMedium.copy( // Using custom medium font
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.2f),
                    color = Color(0xFFED6825),
                )
            )
        }

        // Saved Time Button
        if (state.savedTime != 0 && !state.saveTimeClicked) {
            Button(
                onClick = {
                    eventPublisher(PaymentContract.PaymentContactUiEvent.SaveTimeClicked(true))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(72.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFED6825)
                ),
                shape = RoundedCornerShape(24.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Use saved time",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.3f),
                            color = Color.White
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = "Arrow",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 36.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = if (state.saveTimeClicked) "0:00" else "${state.savedTime / 60}:${state.savedTime % 60}",
                            style = poppinsBold.copy(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.3f),
                                color = Color.White
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } else if (state.saveTimeClicked){
            Button(
                onClick = {
                    eventPublisher(PaymentContract.PaymentContactUiEvent.SaveTimeClicked(false))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(72.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFED6825)
                ),
                shape = RoundedCornerShape(24.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Don't use saved time",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.3f),
                            color = Color.White
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = "Arrow",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 36.dp)
                                .size(24.dp)
                        )
                    }
                }
            }
        }

        // Token Adjuster
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { eventPublisher(PaymentContract.PaymentContactUiEvent.ChangeTokenAmount(false)) },
                    modifier = Modifier.size(86.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(50),
                    enabled = state.token > 0
                ) {
                    Text(
                        text = "−",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = Color.White
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "${state.token}",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "TOKENS",
                        style = poppinsMedium.copy(
                            color = Color(0xFFED6825)
                        )
                    )
                }

                Button(
                    onClick = { eventPublisher(PaymentContract.PaymentContactUiEvent.ChangeTokenAmount(true)) },
                    modifier = Modifier.size(86.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "+",
                        style = poppinsBold.copy(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = Color.White
                        )
                    )
                }
            }

            Text(
                text = "Time: ${state.time / 60}:${state.time % 60}",
                style = poppinsMedium.copy(
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Pricing Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .background(
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Base Price",
                    style = poppinsMedium.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.2f),
                        color = Color.Black
                    )
                )
                Text(
                    text = "€${state.basePrice}",
                    style = poppinsMedium.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.2f),
                        color = Color.Black
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Member Savings",
                    style = poppinsMedium.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.2f),
                        color = Color.Red
                    )
                )
                Text(
                    text = "-€${state.discountToShow}",
                    style = poppinsMedium.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.2f),
                        color = Color.Red
                    )
                )
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total Price:",
                    style = poppinsBold.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.3f),
                        color = Color.Black
                    )
                )
                Text(
                    text = "€${state.screenTotalPrice}",
                    style = poppinsBold.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.3f),
                        color = Color.Black
                    )
                )
            }
        }

        // Pay Button
        Button(
            onClick = {
                println("----Token: ${state.token} Total Price: ${state.totalPrice} Time: ${state.time} Screen Total Price: ${state.screenTotalPrice}")
                if (state.token > 0) {
                    eventPublisher(PaymentContract.PaymentContactUiEvent.PayCLick(state.totalPrice.toString()))
                } else {
                    onUserClick("washer")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50),
            enabled = state.totalPrice > 0f || state.time > 0
        ) {
            Text(
                text = if (state.token > 0) "Pay €${state.screenTotalPrice}" else "Finish",
                style = poppinsBold.copy(
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    color = Color.White
                )
            )
        }
    }
}