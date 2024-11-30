package com.example.payten_windowsxp_userapp.Users.user.userhomescreen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular

fun NavGraphBuilder.userHomeScreen(
    route: String,
    onBonusClick: () -> Unit,
    onQrClick: () -> Unit,
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
        onQrClick = onQrClick
    )
}

@Composable
fun UserHomeScreen(
    state: UserHomeScreenContract.UserHomeScreenState,
    eventPublisher: (uiEvent: UserHomeScreenContract.UserHomeScreenEvent) -> Unit,
    onBonusClick: () -> Unit,
    onQrClick: () -> Unit
) = if (state.loading) {
    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
} else {
    Scaffold(
        containerColor = Color(0xFF212121) // Boja pozadine Scaffold-a
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp), // Horizontalno margina
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.1f)) // Prostor na vrhu da sadržaj bude niže

            // Naslov dobrodošlice
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFED6825),
                        shape = MaterialTheme.shapes.medium
                    ) // Orange pozadina
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

            // Bonus poeni
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF333333),
                        shape = MaterialTheme.shapes.medium
                    ) // Grey pozadina
                    .padding(horizontal = 16.dp, vertical = 12.dp) // Horizontalno i vertikalno padding
                    .clickable { onBonusClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically // Vertikalno centriranje
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

            // Najbliža stanica
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF333333),
                        shape = MaterialTheme.shapes.medium
                    ) // Grey pozadina
                    .padding(16.dp)
            ) {
                // Naslov sekcije
//                Text(
//                    text = "Nearest car wash station",
//                    style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
//                    color = Color.White
//                )

                // Podnaslov sa nazivom
                Text(
                    text = "Self-service car wash, Belgrade",
                    style = poppinsBold.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp)) // Razmak ispod podnaslova

                // Red za ikonicu automobila i udaljenost
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp) // Mali razmak ispod reda
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_car2_icon),
                        contentDescription = "Car Icon",
                        tint = Color(0xFFED6825), // Narandžasta boja ikone
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Razmak između ikone i teksta
                    Text(
                        text = "1.7km, 6min",
                        style = poppinsRegular.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                        color = Color.Gray
                    )
                }

                // Red za ikonicu sata i dostupnost
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock_icon),
                        contentDescription = "Clock Icon",
                        tint = Color(0xFFED6825), // Narandžasta boja ikone
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Razmak između ikone i teksta
                    Text(
                        text = "24/7",
                        style = poppinsRegular.copy(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                        color = Color.Gray
                    )
                }
            }

            // Transakcije sekcija
            Text(
                text = "Recent Transactions",
                style = poppinsBold.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // LazyColumn za transakcije
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Omogućava skrolovanje unutar dostupnog prostora
            ) {
                items(state.transactions) { transaction ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFF333333),
                                shape = MaterialTheme.shapes.medium
                            ) // Grey pozadina
                            .padding(horizontal = 16.dp, vertical = 10.dp) // Unutrašnji razmak
                    ) {
                        // Leva strana - opis i datum u koloni
                        Column(
                            modifier = Modifier.weight(1f) // Zauzima sav raspoloživ prostor na levoj strani
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

                        // Desna strana - poeni
                        Text(
                            text = "+${transaction.bonusPoints} pts",
                            style = poppinsMedium.copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                            color = Color(0xFFED6825), // Narandžasti tekst za poene
                            modifier = Modifier.padding(top = 8.dp) // Blago spušta poene
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Razmak između stavki
                }
            }

            //Spacer(modifier = Modifier.weight(0.1f)) // Prostor na dnu za balansiranje sadržaja
        }
    }
}

