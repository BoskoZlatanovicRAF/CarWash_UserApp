package com.cyb.payten_windowsxp_terminalapp.payment

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

import kotlinx.serialization.Serializable

@Serializable
data class RequestJson(
    @SerializedName("header")
    val header: Header,
    @SerializedName("request")
    val request: Request
) {
    override fun toString(): String {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
        return gson.toJson(this)
    }
}

@Serializable
data class Header(
    @SerializedName("length")
    val length: String = "259",
    @SerializedName("hash")
    val hash: String = "XXX",
    @SerializedName("version")
    val version: String = "01"
)
@Serializable
data class Request(
    @SerializedName("financial")
    val financial: Financial
)
@Serializable
data class Financial(
    @SerializedName("transaction")
    val transaction: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("amounts")
    val amounts: Amounts,
    @SerializedName("options")
    val options: Options
)
@Serializable

data class Id(
    @SerializedName("XXXecr")
    val XXXecr: String = "000008",
    @SerializedName("XXXacquirer")
    val XXXacquirer: String = "AQ2",
    @SerializedName("XXXcardName")
    val XXXcardName: String = "kukuruz|B1|kokice"
)
@Serializable
data class Amounts(
    @SerializedName("base")
    val base: String,
    @SerializedName("currencyCode")
    val currencyCode: String = "RSD"
)
@Serializable
data class Options(
    @SerializedName("language")
    val language: String = "sr",
    @SerializedName("print")
    val print: String = "true"
)
