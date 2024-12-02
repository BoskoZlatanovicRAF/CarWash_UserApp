package com.example.payten_windowsxp_userapp.Users.user.Notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.Notifications.db.Notification
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular

fun NavGraphBuilder.notificationScreen(
    route: String,
    onUserClick: () -> Unit
) = composable(
    route = route,
) {
    val notifcationViewModel: NotificationViewModel = hiltViewModel<NotificationViewModel>()
    val state = notifcationViewModel.state.collectAsState()

    NotificationScreen(
        state = state.value,
        onUserClick = onUserClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    state: NotificationScreenContract.NotificationUiState,
    onUserClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(
                            "<",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            style = poppinsBold,
                            modifier = Modifier.clickable(
                                onClick = { onUserClick() }
                            )
                        )
                        Spacer(modifier = Modifier.width(9.dp))
                        Text(
                            "Notifications",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            style = poppinsBold,
                        )
                    }
                },
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_notifications_none_24),
                        contentDescription = null,
                        tint = Color(0xFFED6825),
                        modifier = Modifier.size(48.dp) // Ikonica će biti 48x48 dp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF212121)
                ),
                modifier = Modifier.padding(12.dp)
            )
        },
        containerColor = Color(0xFF212121)
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .background(Color(0xFF212121))
                .padding(16.dp)
        ) {
            items(state.notifcations) { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: Notification
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = if (notification.isRead == 0) {
            cardColors(containerColor = Color(0xFF3D3D3D))
        } else {
            cardColors(containerColor = Color(0x803D3D3D))
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = notification.imageId),
                contentDescription = null,
                tint = Color(0xFFED6825),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = poppinsBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.text,
                    style = poppinsRegular,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    style = poppinsMedium,
                    color = Color(0xFFED6825),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


