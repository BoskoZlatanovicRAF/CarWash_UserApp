package com.example.payten_windowsxp_userapp.Users.repository

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Token
import com.example.payten_windowsxp_userapp.db.AppDatabase
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val database: AppDatabase
) {

        suspend fun getAllTokens(): List<Token> {
            return database.tokenDao().getAllTokens()
        }

        suspend fun insertToken(token: Token) {
            database.tokenDao().insertToken(token)
        }
        suspend fun insertAllTokens(token: List<Token>) {
            database.tokenDao().insertAllTokens(token)
        }

        suspend fun getNumberOfTokens(): Int {
            return database.tokenDao().getNumberOfTokens()
        }
}
