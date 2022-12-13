package com.rapptrlabs.androidtest.api

import com.rapptrlabs.androidtest.features.chat.data.ChatApiData
import com.rapptrlabs.androidtest.features.login.data.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    companion object {
        const val BASE_URL: String = "http://dev.rapptrlabs.com/Tests/scripts/"
    }

    @GET("chat_log.php")
    suspend fun getChatMessages(): ChatApiData

    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(@Field("email") email: String, @Field("password") password: String): Response<LoginResponseDto>


}