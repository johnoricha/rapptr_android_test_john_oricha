package com.rapptrlabs.androidtest.repository

import com.rapptrlabs.androidtest.features.chat.data.ChatMessageModel
import com.rapptrlabs.androidtest.features.login.data.LoginResponseModel
import com.rapptrlabs.androidtest.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getChats() : Flow<Resource<List<ChatMessageModel>>>

    suspend fun login(email: String, password: String): Flow<Resource<LoginResponseModel>>
}