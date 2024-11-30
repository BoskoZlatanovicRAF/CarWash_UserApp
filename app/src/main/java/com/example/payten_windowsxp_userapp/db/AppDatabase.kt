package com.example.payten_windowsxp_userapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Token
import com.example.payten_windowsxp_userapp.Users.User
import com.example.payten_windowsxp_userapp.Users.db.LocalDao
import com.example.payten_windowsxp_userapp.Users.db.TokenDao
import com.example.payten_windowsxp_userapp.Users.db.UserDao
import com.example.payten_windowsxp_userapp.Users.user.db.Transaction
import com.example.payten_windowsxp_userapp.Users.user.db.TransactionDao

@Database(
    entities = [
        User::class,
        Local::class,
        Token::class,
        Transaction::class,
    ],
    version = 12,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun localDao() : LocalDao
    abstract fun tokenDao() : TokenDao
    abstract fun transactionDao() : TransactionDao
}