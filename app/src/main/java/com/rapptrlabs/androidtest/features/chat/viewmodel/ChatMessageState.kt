package com.rapptrlabs.androidtest.features.chat.viewmodel

import com.rapptrlabs.androidtest.features.chat.data.ChatMessageModel

data class ChatMessageState(
    val isLoading: Boolean = false,
    val data: List<ChatMessageModel> = emptyList(),
    val errorMsg: String? = null
)