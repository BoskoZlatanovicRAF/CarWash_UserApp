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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val screens = listOf(
        Screen.Home,
        Screen.Location,
        Screen.QR,
        Screen.Notifications,
        Screen.Profile
    )

    NavigationBar(
        containerColor = Color(0xFF333333),
        contentColor = Color(0xFFCCCCCC)
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { if (currentRoute != screen.route) navController.navigate(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFED6825),
                    unselectedIconColor = Color.White,
                    indicatorColor = Color(0xFF212121)
                ),
                icon = {
                    when (screen) {
                        is Screen.VectorScreen -> Icon(
                            imageVector = if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = screen.route
                        )
                        is Screen.DrawableScreen -> {
                            if (screen == Screen.QR) {
                                Box(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon),
                                        contentDescription = screen.route,
                                        modifier = Modifier.size(45.dp)  // Larger size for QR icon
                                    )
                                }
                            } else {
                                Icon(
                                    painter = painterResource(if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon),
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