package com.example.payten_windowsxp_userapp.Navigation.bottomBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import com.example.payten_windowsxp_userapp.auth.AuthStore
import java.util.Locale

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?
) {
    val viewModel: BottomNavigationViewModel = hiltViewModel()
    val userRole by viewModel.userRole.collectAsState()

    val screens = when (userRole) {
        RoleEnum.ADMIN -> listOf(
            Screen.AdminHome,
            Screen.AdminProfile
        )
        else -> listOf(
            Screen.Home,
            Screen.Location,
            Screen.QR,
            Screen.Notifications,
            Screen.Profile
        )
    }

    NavigationBar(
        containerColor = Color(0xFF333333),
        contentColor = Color(0xFFCCCCCC)
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute?.startsWith(screen.route) == true,
                onClick = { if (currentRoute != screen.route) navController.navigate(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFED6825),
                    unselectedIconColor = Color.White,
                    indicatorColor = Color(0xFF212121)
                ),
                icon = {
                    when (screen) {
                        is Screen.VectorScreen ->
                            Icon(
                                imageVector = if (currentRoute?.startsWith(screen.route) == true) screen.selectedIcon
                                else screen.unselectedIcon,
                                contentDescription = screen.route
                            )
                        is Screen.DrawableScreen -> {
                            if (screen == Screen.QR) {
                                Box(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            if (currentRoute?.startsWith(screen.route) == true) screen.selectedIcon
                                            else screen.unselectedIcon
                                        ),
                                        contentDescription = screen.route,
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    painter = painterResource(
                                        if (currentRoute?.startsWith(screen.route) == true) screen.selectedIcon
                                        else screen.unselectedIcon
                                    ),
                                    contentDescription = screen.route
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun rememberUserRole(authStore: AuthStore): State<RoleEnum> {
    return produceState(initialValue = RoleEnum.USER) {
        authStore.authData.collect { authData ->
            value = authData.role
        }
    }
}