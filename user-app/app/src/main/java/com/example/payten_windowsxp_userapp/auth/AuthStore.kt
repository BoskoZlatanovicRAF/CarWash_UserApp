package com.example.payten_windowsxp_userapp.auth

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStore @Inject constructor(
    private val persistence: DataStore<AuthData>
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    val authData = persistence.data
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = runBlocking { persistence.data.first() },
        )

    suspend fun updateAuthData(newAuthData: AuthData) {
        persistence.updateData { oldAuthData ->
            newAuthData
        }

        Log.d("AuthStore", "Data updated: $newAuthData")
    }
    suspend fun clearAuthData() {
        persistence.updateData { oldAuthData ->
            AuthData(
                id = 0,
                firstname = "",
                lastName = "",
                email = "",
                bonusPoints = 0,
                time = "",
                role = RoleEnum.USER
            )
        }
    }

    suspend fun getAuthData(): AuthData {
        return persistence.data.first()
    }

}