package com.example.payten_windowsxp_userapp.Navigation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val authStore: AuthStore
) : ViewModel() {

    private val _startDestination = MutableStateFlow("login")
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val authData = authStore.getAuthData()
            _startDestination.value = if (authData.firstname.isNotEmpty()) {
                "userHomeScreen"
            } else {
                "login"
            }
        }
    }
}