package com.rapptrlabs.androidtest.features.chat.data

data class ChatMessageDto(
    val avatar_url: String,
    val message: String,
    val name: String,
    val user_id: String
) {
    fun toChatLogMessageModel(): ChatLogMessageModel = ChatLogMessageModel(
        userId = user_id,
        avatarUrl = avatar_url,
        username = name,
        message = message
    )
}

