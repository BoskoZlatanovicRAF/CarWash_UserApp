package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold

fun NavGraphBuilder.localScreen(
    route: String,
    onBackClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val localViewModel: LocalViewModel = hiltViewModel(navBackStackEntry)
    val state = localViewModel.state.collectAsState()
    LocalScreen(
        state = state.value,
        onBackClick = onBackClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalScreen(
    state: LocalState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Local details",
                            style = poppinsBold.copy(fontSize = 36.sp),
                            color = Color.LightGray,
                            modifier = Modifier
                                .padding(end = 16.dp),
                        )
                        Text(
                            text = "<",
                            style = poppinsBold.copy(fontSize = 36.sp),
                            color = Color(0xFFED6825),
                            modifier = Modifier
                                .clickable { onBackClick() }
                                .padding(end = 16.dp)
                        )
                    }

                },
                colors = topAppBarColors(
                    containerColor = Color(0xFF212121)
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF212121))
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
                            text = "No data available for the selected station.",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = cardColors(
                                containerColor = Color(0xFF333333)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = state.local.name,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                                        contentDescription = "Clock Icon",
                                        tint = Color(0xFFED6825),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = " " + state.local.address,
                                        fontSize = 16.sp,
                                        color = Color.LightGray
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_car_icon),
                                        contentDescription = "Clock Icon",
                                        tint = Color(0xFFED6825),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = " " + "${state.local.boxNumber} washing Boxes",
                                        fontSize = 16.sp,
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Token price",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = androidx.compose.material3.CardDefaults.cardColors(
                                containerColor = Color(0xFF333333)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                state.tokens?.forEach { token ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        horizontalArrangement = Arrangement.Center // Centriranje sadržaja u Row
                                    ) {
                                        Text(
                                            text = "${token.timeStart} - ${token.timeEnd}",
                                            fontSize = 18.sp,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(8.dp)) // Razmak između tekstova
                                        Text(
                                            text = "  .................  ${token.price} €",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFED6825)
                                        )
                                    }

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFED6825),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Edit token price",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
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
