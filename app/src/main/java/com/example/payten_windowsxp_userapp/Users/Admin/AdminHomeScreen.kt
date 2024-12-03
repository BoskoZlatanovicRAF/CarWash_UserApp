package com.example.payten_windowsxp_userapp.Users.Admin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.LocalUI
import kotlin.random.Random

fun NavGraphBuilder.adminHomeScreen(
    route: String,
    onItemClick: (String) -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val adminViewModel: AdminViewModel = hiltViewModel(navBackStackEntry)
    val state = adminViewModel.state.collectAsState()

    AdminHomeScreen(
        state = state.value,
        onItemClick = onItemClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    state: AdminState,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "List of Car Wash Shops",
                        color = Color.White,
                        fontWeight = Bold
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color(0xFF212121)
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                containerColor = Color(0xFFED6825)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new car wash shop",
                    tint = Color.White
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF212121))
            ) {
                if (!state.fetching) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(state.locals) { local ->
                            LocalCard(
                                local = local,
                                onItemClick = onItemClick
                            )
                        }
                    }
                } else {
                    Loading()
                }
            }
        }
    )
}

@Composable
fun LocalCard(
    local: LocalUI,
    onItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(local.id) },
        shape = RoundedCornerShape(16.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color(0xFF333333)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = local.name,
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = local.address,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "● ",
                        fontSize = 16.sp,
                        color = Color(0xFF00FF00)
                    )
                    Text(
                        text = "Live camera",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
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
