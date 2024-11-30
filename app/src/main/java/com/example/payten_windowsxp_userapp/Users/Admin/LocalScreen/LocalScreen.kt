package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.localScreen(
    route: String,
) = composable(
    route = route,
) { navBackStackEntry ->

    val localViewModel: LocalViewModel = hiltViewModel(navBackStackEntry)
    val state = localViewModel.state.collectAsState()
    LocalScreen(
        state = state.value,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalScreen(
    state: LocalState,
) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(text = "Local Details", color = Color.White) },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF212121) // Background color for the top bar
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF212121)) // Background color for the screen
                    .padding(paddingValues)
            ) {
                if (state.fetching) {
                    Loading()
                } else if (state.local == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No data available for the selected local.",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = androidx.compose.material3.CardDefaults.cardColors(
                            containerColor = Color(0xFF333333) // Grey background for the card
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Name: ${state.local.name}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Address: ${state.local.address}",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                items(state.tokens.orEmpty()) { token ->
                                    Row {
                                        Text(
                                            text = token.timeStart + " - " + token.timeEnd,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "${token.price} €",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color(0xFFED6825)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Box Number: ${state.local.boxNumber}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading...", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = Color(0xFFED6825))
        }
    }
}
