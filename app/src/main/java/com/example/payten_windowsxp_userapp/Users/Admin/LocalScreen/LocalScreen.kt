package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
    onItemClick: (String) -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val localViewModel: LocalViewModel = hiltViewModel(navBackStackEntry)
    val state = localViewModel.state.collectAsState()
    LocalScreen(
        state = state.value,
        onItemClick = onItemClick,
        eventPublisher = {
            localViewModel.setEvent(it)
        },
    )
}
@Composable
fun LocalScreen(
    state: LocalState,
    onItemClick: (String) -> Unit,
    eventPublisher: (LocalState.Events) -> Unit,
) {
    if (state.fetching) {
        Loading()
    } else if (state.local == null) {
        // Dodaj poruku za slučaj kada nema podataka
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onItemClick(state.local.id.toString()) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Name: ${state.local.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Address: ${state.local.address}",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Token Price: ${state.local.tokenPrice} tokens",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Box Number: ${state.local.boxNumber}",
                    fontSize = 16.sp,
                )
            }
        }
    }
}


@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
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