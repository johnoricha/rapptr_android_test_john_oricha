package com.rapptrlabs.androidtest.features.login

import com.rapptrlabs.androidtest.features.login.data.LoginResponseModel

sealed class LoginEvent {
    object Loading: LoginEvent()
    data class LoginSuccess(val data: LoginResponseModel): LoginEvent()
    data class LoginFailed(val data: LoginResponseModel): LoginEvent()
}