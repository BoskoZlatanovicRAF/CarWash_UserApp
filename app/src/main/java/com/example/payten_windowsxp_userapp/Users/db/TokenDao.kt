package com.example.payten_windowsxp_userapp.Users.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Token

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: Token)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTokens(token: List<Token>)


    @Query("SELECT * FROM tokens")
    suspend fun getAllTokens(): List<Token>

    @Query("SELECT COUNT(*) FROM tokens")
    suspend fun getNumberOfTokens(): Int

}