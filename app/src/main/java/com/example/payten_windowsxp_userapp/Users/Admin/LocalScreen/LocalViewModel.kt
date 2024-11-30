package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Navigation.localId
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Token
import com.example.payten_windowsxp_userapp.Users.repository.LocalsRepository
import com.example.payten_windowsxp_userapp.Users.repository.TokenRepository
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
    private val repository: LocalsRepository,
    private val repositoryToken: TokenRepository
) : ViewModel() {
    private val localId: String = savedStateHandle.localId
    private val _state = MutableStateFlow(LocalState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LocalState.() -> LocalState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<LocalState.Events>()
    fun setEvent(event: LocalState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        insertTokens()
        loadTokens()
        loadLocal()
    }

    private fun loadTokens() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val tokens = repositoryToken.getAllTokens()
                setState { copy(tokens = tokens) }
            } catch (error: Exception) {
                println("Error: ${error.message}")
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

    private fun insertTokens() {
        viewModelScope.launch {
            if(repositoryToken.getNumberOfTokens() < 5) {
                val token1 = Token(
                    id = 0,
                    locationID = "1",
                    timeStart = "12:18",
                    timeEnd = "11:00",
                    price = 10,
                )
                val token2 = Token(
                    id = 0,
                    locationID = "1",
                    timeStart = "18:00",
                    timeEnd = "12:00",
                    price = 10,
                )
                val token3 = Token(
                    id = 0,
                    locationID = "1",
                    timeStart = "00:00",
                    timeEnd = "6:00",
                    price = 10,
                )
                val token4 = Token(
                    id = 0,
                    locationID = "1",
                    timeStart = "06:00",
                    timeEnd = "12:00",
                    price = 10,
                )
                repositoryToken.insertAllTokens(listOf(token1, token2, token3, token4))
            }
        }
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
