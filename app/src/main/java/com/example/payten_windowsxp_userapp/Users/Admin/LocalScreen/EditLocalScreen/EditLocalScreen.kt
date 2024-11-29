package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.EditLocalScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.editLocalScreen(
    route: String,
) = composable(
    route = route,
) { navBackStackEntry ->

    val editLocalViewModel: EditLocalViewModel = hiltViewModel(navBackStackEntry)
    val state = editLocalViewModel.state.collectAsState()
    EditLocalScreen(
        state = state.value,
        eventPublisher = {
            editLocalViewModel.setEvent(it)
        },
    )
}

@Composable
fun EditLocalScreen(
    state: EditLocalState,
    eventPublisher: (EditLocalState.Events) -> Unit,
) {


}
