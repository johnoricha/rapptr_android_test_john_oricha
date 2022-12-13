package com.rapptrlabs.androidtest

import com.rapptrlabs.androidtest.features.chat.viewmodel.ChatViewModel
import com.rapptrlabs.androidtest.features.chat.data.ChatLogMessageModel
import com.rapptrlabs.androidtest.repository.Repository
import com.rapptrlabs.androidtest.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class ChatViewModelTest {
    private lateinit var chatViewModel: ChatViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Mock
    lateinit var repository: Repository


    lateinit var closeable: AutoCloseable

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(ChatViewModel::class.java)
        chatViewModel = ChatViewModel(repository)
        Dispatchers.setMain(mainThreadSurrogate)

    }

    @After
    @Throws(Exception::class)
    fun releaseMocks() {
        closeable.close()
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getChats function`(): Unit = runTest {
        val response = Resource.Success(
            listOf(
                ChatLogMessageModel(
                    "1",
                    "avatar1",
                    "user1",
                    "message1"
                ),
                ChatLogMessageModel(
                    "2",
                    "avatar",
                    "user2",
                    "message2"
                ),
                ChatLogMessageModel(
                    "3",
                    "avatar3",
                    "user3",
                    "message3"
                ),
            )
        )

        launch {
            Mockito.`when`(repository.getChats()).thenReturn(
                flow {
                    emit(response)
                }
            )

            chatViewModel.getChats()

            Mockito.verify(repository, Mockito.times(1)).getChats()
        }




    }

}