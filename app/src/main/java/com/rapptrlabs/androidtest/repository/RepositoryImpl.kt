package com.rapptrlabs.androidtest.repository

import com.google.gson.Gson
import com.rapptrlabs.androidtest.api.ApiService
import com.rapptrlabs.androidtest.features.chat.data.ChatLogMessageModel
import com.rapptrlabs.androidtest.features.login.data.LoginResponseDto
import com.rapptrlabs.androidtest.features.login.data.LoginResponseModel
import com.rapptrlabs.androidtest.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override suspend fun getChats(): Flow<Resource<List<ChatLogMessageModel>>> = flow {

        emit(Resource.Loading())

        try {
            val apiData = apiService.getChatMessages().data

            val chatMessages: List<ChatLogMessageModel> = apiData.map { it.toChatLogMessageModel() }

            emit(Resource.Success(data = chatMessages))

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "oops! something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Could not reach server",
                    data = null
                )
            )
        }

    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<LoginResponseModel>> =
        flow {
            emit(Resource.Loading())
            try {

                with(apiService.login(email, password)) {
                    if (this.code() >= 400) {
                        val loginResponseDto = Gson().fromJson(this.errorBody()?.string(), LoginResponseDto::class.java )
                        emit(
                            Resource.Error(
                                message = "oops! something went wrong",
                                data = loginResponseDto.toLoginResponseModel()
                            )
                        )
                    }

                    else if (this.code() in 200..299) {

                        this.body()?.let {
                            it.sentRequestMillis = this.raw().sentRequestAtMillis
                            it.receivedResponseMillis = this.raw().receivedResponseAtMillis
                            emit(Resource.Success(data = it.toLoginResponseModel()))
                        }
                    }

                }


            } catch (e: HttpException) {

                emit(
                    Resource.Error(
                        message = "oops! something went wrong",
                        data = null
                    )
                )
            } catch (e: IOException) {

                emit(
                    Resource.Error(
                        message = "Could not reach server",
                        data = null
                    )
                )
            }

        }
}