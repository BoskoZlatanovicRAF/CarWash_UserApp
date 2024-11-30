package com.example.payten_windowsxp_userapp.Login


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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.Registration.RegisterState
import com.example.payten_windowsxp_userapp.Registration.registrationInput
import okhttp3.internal.wait


fun NavGraphBuilder.logIn(
route: String,
onUserClick: (String) -> Unit
) = composable(
route = route
) {
    val loginScreenViewModel: LoginScreenViewModel = hiltViewModel<LoginScreenViewModel>()
    val state = loginScreenViewModel.state.collectAsState()

    LoginScreen(
        state = state.value,
        eventPublisher = {
            loginScreenViewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
state: LoginScreenContract.LoginScreenUiState,
eventPublisher: (uiEvent: LoginScreenContract.LoginScreenUiEvent) -> Unit,
onUserClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        containerColor = Color(0xFF212121),
        content = { paddingValues ->
            var password by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            if (state.User != null && state.isAdmin) {
                onUserClick("adminHomeScreen")
            } else if (state.User != null) {
                onUserClick("registerScreen") //-------------------------------
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_car_icon),
                    contentDescription = null,
                    tint = Color(0xFFED6825),
                    modifier = Modifier.size(90.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Sign in to your\n\nAccount",
                    fontSize = 42.sp,
                    fontWeight = Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Text(
                        text = "Don’t have an account?",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.clickable {
                        }
                    )
                    Text(
                        text = " Sign Up",
                        fontSize = 16.sp,
                        color = Color(0xFFED6825),
                        modifier = Modifier.clickable {
                            onUserClick("registerScreen")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                loginInput(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                )
                loginInput(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Forgot Your Password ?",
                    fontSize = 18.sp,
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                        }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        eventPublisher(
                            LoginScreenContract.LoginScreenUiEvent.checkLogin(
                                email,
                                password
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFED6825),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Log In",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginInput(
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
            label = { Text(label, color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Dark grey
                focusedBorderColor = Color(0xFFED6825), // Orange
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )
    }
}

