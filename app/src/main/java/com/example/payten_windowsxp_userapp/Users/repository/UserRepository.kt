package com.example.payten_windowsxp_userapp.Users.repository

import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.db.AppDatabase
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getUserCount(): Int {
        return database.userDao().getUserCount()
    }
    suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }
    suspend fun getUserByID(id : Int): User {
        return database.userDao().getUserByID(id)
    }

    suspend fun getByEmailandPassword(email: String, password: String): User {
       return database.userDao().getByEmailandPassword(email, password)
    }

}