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
            val debugLocal = local // Temporary variable for debugging
            repository.insertLocal(debugLocal)
        }
    }

    private fun loadLocals() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val locals = repository.getAllLocal()
                setState { copy(locals = locals) }
            } catch (error: Exception) {
                println("Error: ${error.message}")
            } finally {
                setState { copy(fatching = false) }
            }
        }
    }


    // If you need to add a local, use this function instead
    fun addNewLocal(name: String, address: String, tokenPrice: Int, boxNumber: Int) {
        viewModelScope.launch {
            val local = Local(
                id = 0L,  // Let Room handle the ID generation
                name = name,
                address = address,
                tokenPrice = tokenPrice,
                boxNumber = boxNumber
            )
            repository.insertLocal(local)
            loadLocals()  // Refresh the list after inserting
        }
    }

    // If you need to initialize with default data, use this function
    // and call it only when needed (e.g., from a setup screen)
    fun initializeDefaultLocalIfNeeded() {
        viewModelScope.launch {
            val existingLocals = repository.getAllLocal()
            if (existingLocals.isEmpty()) {
                val local = Local(
                    id = 0L,
                    name = "Local1",
                    address = "Address1",
                    tokenPrice = 150,
                    boxNumber = 10
                )
                repository.insertLocal(local)
                loadLocals()
            }
        }
    }
}