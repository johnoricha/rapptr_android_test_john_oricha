package com.rapptrlabs.androidtest.features.login.data

data class LoginResponseDto(
    val code: String,
    val message: String,
    var sentRequestMillis: Long,
    var receivedResponseMillis: Long,
) {
    fun toLoginResponseModel(): LoginResponseModel {
        return LoginResponseModel(
            code = code,
            message = message,
            callDuration = receivedResponseMillis - sentRequestMillis
        )

    }
}