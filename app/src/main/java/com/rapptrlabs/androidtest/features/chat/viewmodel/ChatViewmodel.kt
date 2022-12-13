package com.rapptrlabs.androidtest.features.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rapptrlabs.androidtest.features.chat.viewmodel.ChatMessageState
import com.rapptrlabs.androidtest.repository.Repository
import com.rapptrlabs.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatMessageState())
    val state = _state.asStateFlow()


    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch {
            chatRepository.getChats().collect {
                when (it) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )

                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            data = it.data!!
                        )

                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            errorMsg = it.message
                        )

                    }

                }
            }
        }

    }
}
