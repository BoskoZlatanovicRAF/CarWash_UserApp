package com.example.payten_windowsxp_userapp.Users.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local

@Dao
interface LocalDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLocal(local: Local)

    @Query("DELETE FROM locals WHERE id = :id")
    suspend fun deleteLocalByID(id: Int)

    @Query("SELECT * FROM locals WHERE name = :name")
    suspend fun getLocalByName(name: String): Local

    @Query("SELECT * FROM locals WHERE id = :id")
    suspend fun getLocalById(id: String): Local

    @Query("SELECT * FROM locals")
    suspend fun getAllLocals(): List<Local>



}