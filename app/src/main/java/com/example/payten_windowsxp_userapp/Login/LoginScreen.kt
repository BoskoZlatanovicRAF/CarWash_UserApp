package com.example.payten_windowsxp_userapp.Login


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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Registration.RegisterState
import com.example.payten_windowsxp_userapp.Registration.registrationInput

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

    Scaffold(
        topBar = {
            // TopAppBar is currently commented out.
        },
        content = { paddingValues ->
            var password by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            if (state.User!=null && state.isAdmin) {
                onUserClick("admin")
            } else if (state.User!=null) {
                onUserClick("user")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                registrationInput(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email"
                )
                registrationInput(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = {
                        eventPublisher(LoginScreenContract.LoginScreenUiEvent.checkLogin(email, password))
                    }
                ) {
                    Text("Register",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(6.dp))
                }
            }
        }
    )
}

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

