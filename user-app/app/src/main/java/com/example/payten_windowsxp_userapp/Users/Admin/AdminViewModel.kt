package com.example.payten_windowsxp_userapp.Users.Admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.mapper.asLocalUI
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
        insertLocals()
        loadLocals()
    }

    private fun insertLocals() {
        viewModelScope.launch {
          val numberOfLocation = repository.getNumberOfLocals()
            if(numberOfLocation <4){
                insertLocal()
                insertLocal2()
                insertLocal3()
                insertLocal4()
            }
        }
    }

    private fun loadLocals() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val locals = repository.getAllLocal()
                setState { copy(locals = locals.map { it.asLocalUI() }) }
            } catch (error: Exception) {
                println("Error: ${error.message}")
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }
    private fun insertLocal4() {
        viewModelScope.launch {
            val local4 = Local(
                id = 4,
                name = "Wash Car 4",
                address = "Beograd, Serbia",
                boxNumber = 7
            )
            repository.insertLocal(local4)
        }
    }

    private fun insertLocal3() {
        viewModelScope.launch {
            val local3 = Local(
                id = 3,
                name = "Wash Car 3",
                address = "Beograd, Serbia",
                boxNumber = 8
            )
            repository.insertLocal(local3)
        }
    }

    private fun insertLocal2() {
        viewModelScope.launch {
            val local2 = Local(
                id = 2,
                name = "Wash Car 2",
                address = "Beograd, Serbia",
                boxNumber = 6
            )
            repository.insertLocal(local2)
        }
    }

    private fun insertLocal() {
        viewModelScope.launch {
            val local = Local(
                id = 1,
                name = "Wash Car 1",
                address = "Beograd, Serbia",
                boxNumber = 10
            )
            repository.insertLocal(local)
        }
    }





}