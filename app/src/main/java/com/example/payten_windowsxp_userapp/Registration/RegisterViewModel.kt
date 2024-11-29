package com.example.payten_windowsxp_userapp.Registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.Users.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()
    private fun setState(reducer: RegisterState.() -> RegisterState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<RegisterState.Events>()
    fun setEvent(event: RegisterState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        existing()
    }

    private fun existing() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val exist = repository.getUserCount() > 0;
                setState { copy(exist = exist) }
            } catch (error: Exception) {
            } finally {
                setState {  copy(fatching = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is RegisterState.Events.Register -> {
                        val fullName = it.fullName
                        val email = it.email
                        val phoneNumber = it.phoneNumber
                        val birthdate = it.birthdate
                        val password = it.password
                        val fullNameRegex = "^[A-Z][a-z]{3,} [A-Z][a-z]{3,}$".toRegex()
                        val emailRegex = "^[A-Za-z0-9_]{3,}@[A-Za-z0-9_]{3,}\\.[A-Za-z0-9_]{2,}$".toRegex()
                        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || birthdate.isEmpty()) {
                            println("Empty fields")
                        } else if (fullName.isNotEmpty() && !fullName.matches(fullNameRegex)) {
                            println("Invalid full name")
                        } else if (email.isNotEmpty() && !email.matches(emailRegex)) {
                            println("Invalid email")
                        } else if (!fullName.isEmpty()) {
                            val user = User(
                                id = 0,
                                firstname = fullName.split(" ")[0],
                                lastName = fullName.split(" ")[1],
                                email = email,
                                phoneNumber = phoneNumber,
                                birthdate = birthdate,
                                password = password,
                                role = RoleEnum.USER
                            )

                            /*
                                 @PrimaryKey(autoGenerate = true) val id: Long,
                                @ColumnInfo(name = "first_name")
                                val firstname: String,
                                @ColumnInfo(name = "last_name")
                                val lastName: String,
                                val email: String,
                                val phoneNumber: String,
                                val birthdate: String,
                                val password: String,
                                val role: RoleEnum,
                                 */
                            try {
                                repository.insertUser(user)
                                setState { copy(SuccesRegister = true) }
                            } catch (error: Exception) {
                                println("Errorr: ${error.message}")
                            }
                        }
                    }
                }
            }
        }
    }

}