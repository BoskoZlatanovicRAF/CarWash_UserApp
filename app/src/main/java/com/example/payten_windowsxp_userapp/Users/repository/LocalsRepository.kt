package com.example.payten_windowsxp_userapp.Users.repository

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.db.AppDatabase
import javax.inject.Inject


class LocalsRepository @Inject constructor(
    private val database: AppDatabase
) {

    suspend fun getAllLocal(): List<Local> {
        return database.localDao().getAllLocals()
    }

    suspend fun getLocalByID(localId: String): Local {
        return database.localDao().getLocalById(localId)
    }

    suspend fun insertLocal(local: Local) {
        database.localDao().insertLocal(local)
    }

    //getNumberOfLocals
    suspend fun getNumberOfLocals(): Int {
        return database.localDao().getNumberOfLocals()
    }
}