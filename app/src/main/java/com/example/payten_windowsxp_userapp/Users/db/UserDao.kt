package com.example.payten_windowsxp_userapp.Users.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.payten_windowsxp_userapp.Users.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users WHERE id = :userID")
    suspend fun getUserByID(userID: Int): User

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getByEmailandPassword(email: String, password: String): User

}