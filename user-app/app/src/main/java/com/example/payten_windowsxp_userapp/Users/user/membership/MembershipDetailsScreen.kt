package com.example.payten_windowsxp_userapp.Users.user.membership

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsMedium
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular

fun NavGraphBuilder.membershipDetailsScreen(
    route: String,
    onBackClick: () -> Unit
) = composable(
    route = route,
) {


    MembershipDetailsScreen(
        onBackClick = onBackClick,

    )
}

@Composable
fun MembershipDetailsScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
            .padding(16.dp)
    ) {
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
            "Membership Details",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            style = poppinsBold,
        )

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFED6825)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Current Tier",
                    style = poppinsRegular.copy(
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    color = Color.White
                )
                Text(
                    text = "Gold Member",
                    style = poppinsBold.copy(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = "315 bonus points",
                    style = poppinsMedium.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Progress to Platinum",
                    style = poppinsBold.copy(fontSize = 18.sp),
                    color = Color.White
                )

                LinearProgressIndicator(
                    progress = 0.65f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(vertical = 8.dp),
                    color = Color(0xFFED6825),
                    trackColor = Color.Gray,
                    strokeCap = StrokeCap.Round

                )

                Text(
                    text = "185 points needed",
                    style = poppinsRegular.copy(fontSize = 14.sp),
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Your Benefits",
            style = poppinsBold.copy(fontSize = 20.sp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BenefitCard(
                modifier = Modifier.weight(1f),
                icon = R.drawable.car_tag,
                title = "10% off",
                subtitle = "All services"
            )
            BenefitCard(
                modifier = Modifier.weight(1f),
                icon = R.drawable.baseline_timer_24,
                title = "Priority",
                subtitle = "Skip the queue"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Points History",
            style = poppinsBold.copy(fontSize = 20.sp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf(
                Triple("Vozdovac", "Nov 29, 2024", "+15"),
                Triple("Vracar", "Nov 29, 2024", "+45"),
                Triple("Banovo Brdo", "Nov 29, 2024", "+35")
            )) { (location, date, points) ->
                HistoryItem(location, date, points)
            }
        }
    }
}

@Composable
private fun BenefitCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    subtitle: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color(0xFFED6825),
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = title,
                    style = poppinsBold.copy(fontSize = 16.sp),
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = poppinsRegular.copy(fontSize = 12.sp),
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun HistoryItem(
    location: String,
    date: String,
    points: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
        shape = RoundedCornerShape(12.dp)
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
                    text = location,
                    style = poppinsBold.copy(fontSize = 16.sp),
                    color = Color.White
                )
                Text(
                    text = date,
                    style = poppinsRegular.copy(fontSize = 12.sp),
                    color = Color.Gray
                )
            }
            Text(
                text = points,
                style = poppinsBold.copy(fontSize = 16.sp),
                color = Color(0xFFED6825)
            )
        }
    }
}
