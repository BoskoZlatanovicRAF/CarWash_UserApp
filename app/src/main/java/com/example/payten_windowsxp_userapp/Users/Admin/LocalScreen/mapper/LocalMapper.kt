package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.mapper

import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.Local
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db.LocalUI

fun Local.asLocalUI(): LocalUI {
    return LocalUI(
        id = this.id.toString(),
        name = this.name,
        address = this.address,
        boxNumber = this.boxNumber,
    )
}