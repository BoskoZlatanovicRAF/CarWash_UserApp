package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Navigation.localId
import com.example.payten_windowsxp_userapp.Users.repository.LocalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: LocalsRepository
) : ViewModel() {
    private val localId: Long = savedStateHandle.localId
    private val _state = MutableStateFlow(LocalState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LocalState.() -> LocalState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<LocalState.Events>()
    fun setEvent(event: LocalState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        loadLocal()
    }

    private fun loadLocal() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val local = repository.getLocalByID(localId)
                setState { copy(local = local) }
            } catch (error: Exception) {
                println("Error: ")
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }
}
