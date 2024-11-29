package com.example.payten_windowsxp_userapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.Users.UserDao

@Database(
    entities = [
        User::class,
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
}