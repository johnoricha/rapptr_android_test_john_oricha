package com.rapptrlabs.androidtest

import com.google.common.truth.Truth.assertThat
import com.rapptrlabs.androidtest.features.login.data.LoginResponseModel
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.EMAIL
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.PASSWORD
import com.rapptrlabs.androidtest.repository.Repository
import com.rapptrlabs.androidtest.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    lateinit var repository: Repository


    lateinit var closeable: AutoCloseable


    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this);
        loginViewModel = LoginViewModel(repository)
        Dispatchers.setMain(mainThreadSurrogate)

    }

    @After
    @Throws(Exception::class)
    fun releaseMocks() {
        closeable.close()
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `check email validation`() {
        loginViewModel.setTextFieldValue(EMAIL, "john@gmail.com")
        val value = loginViewModel.checkValidEmail()
        assertThat(value).isTrue()
    }

    @Test
    fun `check email not validate without @`() {
        loginViewModel.setTextFieldValue(EMAIL, "johngmail.com")
        val value = loginViewModel.checkValidEmail()
        assertThat(value).isFalse()
    }

    @Test
    fun `check email not validate without dot com`() {
        loginViewModel.setTextFieldValue(EMAIL, "john@gmail")
        val value = loginViewModel.checkValidEmail()
        assertThat(value).isFalse()
    }


    @Test
    fun `check password validation`() {
        loginViewModel.setTextFieldValue(PASSWORD, "123456@D")
        val value = loginViewModel.checkValidPassword()
        assertThat(value).isTrue()
    }

    @Test
    fun `check password is null`() {
        loginViewModel.setTextFieldValue(PASSWORD, null)
        val value = loginViewModel.checkValidPassword()
        assertThat(value).isFalse()
    }

    @Test
    fun `check password is empty`() {
        loginViewModel.setTextFieldValue(PASSWORD, "")
        val value = loginViewModel.checkValidPassword()
        assertThat(value).isFalse()
    }

    @Test
    fun `text button enabled when email and password valid`() {
        loginViewModel.setTextFieldValue(EMAIL, "john+testing123@rapptrlabs.com")
        loginViewModel.setTextFieldValue(PASSWORD, "Password\$123")

        val enableSignInBtnValue = loginViewModel.enableSignInBtn.value
        assertThat(enableSignInBtnValue).isTrue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test login function`(): Unit = runTest {
        val expectedResponse = Resource.Success(
            LoginResponseModel(
                code = "Success",
                callDuration = 200,
                message = "Login Successful"
            )
        )


        launch {
            Mockito.`when`(repository.login("info@rapptrlabs.com", "Test123"))
                .thenReturn(
                flow {
                    emit(expectedResponse)
                }
            )
            repository.login("info@rapptrlabs.com", "Test123")

            Mockito.verify(repository, Mockito.times(1))
                .login("info@rapptrlabs.com", "Test123")

        }
    }

}