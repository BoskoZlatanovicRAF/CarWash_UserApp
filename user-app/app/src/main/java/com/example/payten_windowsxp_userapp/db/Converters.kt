package com.example.payten_windowsxp_userapp.db

import androidx.room.TypeConverter
import com.example.payten_windowsxp_userapp.Users.RoleEnum


class Converters {
    @TypeConverter
    fun fromRole(role: RoleEnum): String {
        return role.name
    }

    @TypeConverter
    fun toRole(role: String): RoleEnum {
        return RoleEnum.valueOf(role)
    }
}