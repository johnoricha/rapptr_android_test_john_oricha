package com.rapptrlabs.androidtest.features.chat.data

data class ChatLogMessageModel(
    val userId: String,
    val avatarUrl: String,
    val username: String,
    val message: String
)