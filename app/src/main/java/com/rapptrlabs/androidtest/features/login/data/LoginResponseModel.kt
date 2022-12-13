package com.rapptrlabs.androidtest.features.login.data

data class LoginResponseModel(
    val isLoading: Boolean = false,
    val code: String,
    val message: String,
    val callDuration: Long
)
