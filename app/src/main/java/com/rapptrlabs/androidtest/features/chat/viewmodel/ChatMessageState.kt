package com.rapptrlabs.androidtest.features.chat.viewmodel

import com.rapptrlabs.androidtest.features.chat.data.ChatLogMessageModel

data class ChatMessageState(
    val isLoading: Boolean = false,
    val data: List<ChatLogMessageModel> = emptyList(),
    val errorMsg: String? = null
)