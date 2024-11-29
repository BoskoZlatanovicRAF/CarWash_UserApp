package com.example.payten_windowsxp_userapp.Registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.example.payten_windowsxp_userapp.R


fun NavGraphBuilder.registerScreen(
    route: String,
    onItemClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val userViewModel: RegisterViewModel = hiltViewModel(navBackStackEntry)
    val state = userViewModel.state.collectAsState()
    RegisterScreen(
        data = state.value,
        onItemClick = onItemClick,
        eventPublisher = {
            userViewModel.setEvent(it)
        },
    )
}

@Composable
fun RegisterScreen(
    data: RegisterState,
    onItemClick: () -> Unit,
    eventPublisher: (RegisterState.Events) -> Unit,
) {
    if (data.SuccesRegister) {
        onItemClick()
        return
    }
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        content = { paddingValues ->
            if (!data.fatching) {
                var fullName by remember { mutableStateOf("") }
                var birthdate by remember { mutableStateOf("") }
                var phoneNumber by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    registrationInput(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Full name",
                    )
                    registrationInput(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email"
                    )
                    registrationInput(
                        value = birthdate,
                        onValueChange = { birthdate = it },
                        label = "Nickname",
                    )
                    registrationInput(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = "Nickname",
                    )
                    registrationInput(
                            value = password,
                    onValueChange = { password = it },
                    label = "Password"
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = {
                            eventPublisher(RegisterState.Events.Register(fullName, email, birthdate, phoneNumber, password))
                        }
                    ) {
                        Text("Register",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(6.dp))
                    }
                }
            } else {
                LoadingEditProfile()
            }
        }
    )
}

@Composable
fun registrationInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEAEAEA),
                focusedContainerColor = Color(0xFFEAEAEA),
                focusedIndicatorColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
                cursorColor = Color.Black,
            ),
        )
    }
}



@Composable
fun LoadingEditProfile() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
