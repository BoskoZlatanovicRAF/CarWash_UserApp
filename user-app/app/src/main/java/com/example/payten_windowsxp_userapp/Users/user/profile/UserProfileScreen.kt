package com.example.payten_windowsxp_userapp.Users.user.profile

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Login.LoginScreenContract
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.UserHomeScreenViewModel
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium

fun NavGraphBuilder.userProfileScreen(
    route: String,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    navLogOutClick: () -> Unit
) = composable(
    route = route,
) { navBackStackEntry ->

    val userProfileViewModel: UserProfileViewModel = hiltViewModel(navBackStackEntry)
    val state = userProfileViewModel.state.collectAsState()
    UserProfileScreen(
        state = state.value,
        onEditClick = onEditClick,
        onBackClick = onBackClick,
        eventPublisher = {
            userProfileViewModel.setEvent(it)
        },
        navLogOutClick = navLogOutClick
    )
}

@Composable
fun UserProfileScreen(
    state: UserProfileContract.UserProfileState,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    navLogOutClick: () -> Unit,
    eventPublisher: (UserProfileContract.UserProfileScreenUiEvent) -> Unit
) {
    if(state.loggedOut){
        navLogOutClick()
    }else {
        Scaffold(
            containerColor = Color(0xFF212121)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            "<",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            style = poppinsBold,
                            modifier = Modifier.clickable(
                                onClick = { onBackClick() }
                            )
                        )
                        Spacer(modifier = Modifier.width(9.dp))
                        Text(
                            "Profile",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            style = poppinsBold,
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = "Profile Icon",
                        tint = Color(0xFFED6825),
                        modifier = Modifier.size(150.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF333333),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileField(
                            label = "Full Name",
                            value = "${state.firstName} ${state.lastName}",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "Edit Profile",
                            tint = Color(0xFFED6825),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { onEditClick() }
                        )
                    }
                    ProfileField(
                        label = "Email",
                        value = state.email
                    )
                    ProfileField(
                        label = "Date of Birth",
                        value = state.dateOfBirth
                    )
                    ProfileField(
                        label = "Phone",
                        value = state.phoneNumber
                    )
                    ProfileField(
                        label = "Password",
                        value = "●".repeat(state.password.length)
                    )
                }
                Button(
                    onClick = { eventPublisher(UserProfileContract.UserProfileScreenUiEvent.logOutClick()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFED6825),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Log Out",
                        fontSize = 22.sp,
                        style = poppinsBold,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            style = poppinsMedium.copy(fontSize = 14.sp),
            color = Color.Gray
        )
        Text(
            text = value,
            style = poppinsBold.copy(fontSize = 16.sp),
            color = Color.White
        )
    }
}