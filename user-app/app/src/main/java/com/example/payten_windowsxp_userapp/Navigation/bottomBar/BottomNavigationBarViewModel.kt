package com.example.payten_windowsxp_userapp.Navigation.bottomBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(
    private val authStore: AuthStore
) : ViewModel() {
    private val _userRole = MutableStateFlow(RoleEnum.USER)
    val userRole = _userRole.asStateFlow()

    init {
        viewModelScope.launch {
            authStore.authData.collect { authData ->
                _userRole.value = authData.role
            }
        }
    }
}