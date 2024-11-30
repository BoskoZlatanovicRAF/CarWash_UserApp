package com.example.payten_windowsxp_userapp.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.datastore.core.Serializer
import com.example.payten_windowsxp_userapp.Users.RoleEnum
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class AuthDataSerializer : Serializer<AuthData> {

    override val defaultValue: AuthData = AuthData(0,"","","",0, "", RoleEnum.USER)

    override suspend fun readFrom(input: InputStream): AuthData {
        return withContext(Dispatchers.IO) {
            val inputString = input.bufferedReader().use { it.readText() }
            Json.decodeFromString(inputString)
        }
    }

    override suspend fun writeTo(t: AuthData, output: OutputStream) {
        withContext(Dispatchers.IO) {
            val outputString = Json.encodeToString(t)
            output.write(outputString.toByteArray())
        }
    }
}