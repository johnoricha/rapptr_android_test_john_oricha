package com.rapptrlabs.androidtest.features.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.rapptrlabs.androidtest.R
import com.rapptrlabs.androidtest.features.chat.data.ChatLogMessageModel

class ChatAdapter(private var messages: List<ChatLogMessageModel> = mutableListOf()) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var avatarImageView: ImageView
        private var messageTextView: TextView
        private var usernameTextView: TextView
        init {
            avatarImageView = itemView.findViewById(R.id.avatarImageView)
            messageTextView = itemView.findViewById(R.id.messageTextView)
            usernameTextView = itemView.findViewById(R.id.usernameTextView)

        }

        fun bind(message: ChatLogMessageModel) {

            usernameTextView.apply {
                text = message.username
            }

            messageTextView.apply {
                text = message.message
            }
            avatarImageView
                .load(message.avatarUrl) {
                    transformations(CircleCropTransformation())
                }

        }
    }

    fun setList(list: List<ChatLogMessageModel>) {
        messages = list
        notifyDataSetChanged()
    }
}