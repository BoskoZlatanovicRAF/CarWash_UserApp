package com.example.payten_windowsxp_userapp.Users.Admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local


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
        eventPublisher = {
            adminViewModel.setEvent(it)
        },
    )
}

@Composable
fun AdminHomeScreen(
    state: AdminState,
    onItemClick: (String) -> Unit,
    eventPublisher: (AdminState.Events) -> Unit,
) {
    if (!state.fatching) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.locals) { local ->
                LocalCard(
                    local = local,
                    state = state,
                    onItemClick = onItemClick
                )
            }
        }
    }else Loading()

}

@Composable
fun LocalCard(
    local: Local,
    state: AdminState,
    onItemClick: (String) -> Unit
) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onItemClick(state.User?.id.toString()) },
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = local.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = local.name)
                Spacer(modifier = Modifier.height(8.dp))
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


/*

fun NavGraphBuilder.adminHomeScreen(
    route: String,
    onItemClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val adminViewModel: AdminViewModel = hiltViewModel(navBackStackEntry)
    val state = adminViewModel.state.collectAsState()
    AdminHomeScreen(
        data = state.value,
        onItemClick = onItemClick,
        eventPublisher = {
            adminViewModel.setEvent(it)
        },
    )
}

@Composable
fun AdminHomeScreen(
    data: AdminState,
    onItemClick: () -> Unit,
    eventPublisher: (AdminState.Events) -> Unit,
) {

}

 */