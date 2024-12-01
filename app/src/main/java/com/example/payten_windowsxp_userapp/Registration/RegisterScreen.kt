package com.example.payten_windowsxp_userapp.Registration

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.payten_windowsxp_userapp.Login.CustomPasswordVisualTransformation
import com.example.payten_windowsxp_userapp.R
import com.example.payten_windowsxp_userapp.ui.theme.poppinsBold
import com.example.payten_windowsxp_userapp.ui.theme.poppinsRegular
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun NavGraphBuilder.registerScreen(
    route: String,
    onItemClick: () -> Unit,
    onLoginClick: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val userViewModel: RegisterViewModel = hiltViewModel(navBackStackEntry)
    val state = userViewModel.state.collectAsState()
    RegisterScreen(
        data = state.value,
        onItemClick = onItemClick,
        onLoginClick = onLoginClick,
        eventPublisher = {
            userViewModel.setEvent(it)
        },
    )
}

@Composable
fun RegisterScreen(
    data: RegisterState,
    onItemClick: () -> Unit,
    onLoginClick: () -> Unit,
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
        containerColor = Color(0xFF212121), // Postavljanje pozadine na tamno sivu
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_car_icon),
                        contentDescription = null,
                        tint = Color(0xFFED6825),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Create account",
                        fontSize = 36.sp,
                        style = poppinsBold, // Prilagođeni font
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Row {
                        Text(
                            text = "Already have an account?",
                            fontSize = 16.sp,
                            style = poppinsRegular, // Prilagođeni font
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                        Text(
                            text = " Login",
                            fontSize = 16.sp,
                            style = poppinsBold, // Prilagođeni font
                            color = Color(0xFFED6825),
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {
                                    onLoginClick()
                                },
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp), // Nema razmaka između input polja
                        modifier = Modifier.fillMaxWidth()
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

                        DatePickerDocked(
                            onDateSelected = { date ->
                                birthdate = date
                            }
                        )
                        registrationInput(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = "Number",
                        )
                        registrationInput(
                            value = password,
                            onValueChange = { password = it },
                            label = "Password",
                            isPassword = true
                        )
                    }
                    Spacer(modifier = Modifier.height(36.dp))
                    Button(
                        onClick = {
                            eventPublisher(RegisterState.Events.Register(fullName, email, birthdate, phoneNumber, password))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFED6825) // Narandžasta boja za dugme
                        )
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 20.sp,
                            style = poppinsBold, // Prilagođeni font
                            color = Color.White,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            } else {
                LoadingEditProfile()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registrationInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = poppinsRegular.copy(color = Color.Gray) // Prilagođeni font
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color(0xFFED6825), // Orange
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            ),
            textStyle = poppinsRegular.copy(fontSize = 16.sp),
            visualTransformation = if (isPassword) CustomPasswordVisualTransformation() else VisualTransformation.None // Dodaj transformaciju// Prilagođeni font za unos
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Date of Birth") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color(0xFFED6825), // Orange
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                    )
                }
            }
        }
        if (datePickerState.selectedDateMillis != null) {
            val formattedDate = convertMillisToDate(datePickerState.selectedDateMillis!!)
            onDateSelected(formattedDate) // Ažurira roditeljsku komponentu sa izabranim datumom
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
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
            Text(
                text = "Loading...",
                fontSize = 24.sp,
                style = poppinsBold, // Prilagođeni font
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = Color(0xFFED6825)) // Narandžasta boja za indikator
        }
    }
}