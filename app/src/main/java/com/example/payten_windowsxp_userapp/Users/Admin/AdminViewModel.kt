package com.example.payten_windowsxp_userapp.Users.Admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.repository.LocalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: LocalsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AdminState())
    val state = _state.asStateFlow()
    private fun setState(reducer: AdminState.() -> AdminState) =
        _state.getAndUpdate(reducer)

    init {
        insertLocal()
        loadLocals()
    }

    private fun insertLocal() {
        viewModelScope.launch {
            val local = Local(
                id = 0,
                name = "Local1",
                address = "Address1",
                tokenPrice = 150,
                boxNumber = 10
            )
            repository.insertLocal(local)
        }
    }

    private fun loadLocals() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val locals = repository.getAllLocal()
                setState { copy(locals = locals) }
            } catch (error: Exception) {
                println("Errorr: ")
            } finally {
                setState { copy(fatching = false) }
            }
        }
    }
}