package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.EditLocalScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Navigation.editLocalId
import com.example.payten_windowsxp_userapp.Users.repository.LocalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditLocalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: LocalsRepository
) : ViewModel() {
    private val editLocalId: String = savedStateHandle.editLocalId
    private val _state = MutableStateFlow(EditLocalState())
    val state = _state.asStateFlow()
    private fun setState(reducer: EditLocalState.() -> EditLocalState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<EditLocalState.Events>()
    fun setEvent(event: EditLocalState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        loadLocal()
    }

    private fun loadLocal() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val local = repository.getLocalByID(editLocalId)
                setState { copy(local = local) }
            } catch (error: Exception) {
                println("Errorr: ")
            } finally {
                setState { copy(fatching = false) }
            }
        }
    }
}
