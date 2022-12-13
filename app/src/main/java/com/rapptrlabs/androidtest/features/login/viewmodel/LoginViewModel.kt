package com.rapptrlabs.androidtest.features.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rapptrlabs.androidtest.features.login.LoginEvent
import com.rapptrlabs.androidtest.repository.Repository
import com.rapptrlabs.androidtest.util.PasswordRegexes
import com.rapptrlabs.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isFieldValid = MutableStateFlow<Pair<Int, Boolean>?>(null)
    val isFieldValid: StateFlow<Pair<Int, Boolean>?> = _isFieldValid

    private val _enableSignInBtn = MutableStateFlow(false)
    val enableSignInBtn: StateFlow<Boolean> = _enableSignInBtn

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _enableSignInBtn.value = false
    }


    fun setTextFieldValue(id: Int, value: String?) {
        _textFieldMap[id] = value ?: PREFILLED_NULL
        _enableSignInBtn.value = allFilled(_textFieldMap)
                && _textFieldMap[PASSWORD].contains(Regex(PasswordRegexes.AT_LEAST_7_CHARACTERS.pattern()))
    }

    fun checkValidEmail(): Boolean {
        val input = _textFieldMap[EMAIL]
        return when {
            input.isEmpty() -> {
                isAllValidFunc(EMAIL, false)
                false
            }
            !isEmailValid(input) -> {
                isAllValidFunc(EMAIL, false)
                false
            }
            else -> {
                isAllValidFunc(EMAIL, true)
                true
            }
        }
    }

    fun checkValidPassword(): Boolean {
        val input = _textFieldMap[PASSWORD]
        return when {
            input.isEmpty() -> {
                isAllValidFunc(PASSWORD, false)
                false
            }
            !input.contains(Regex(PasswordRegexes.AT_LEAST_7_CHARACTERS.pattern())) -> {
                isAllValidFunc(PASSWORD, false)
                false
            }
            else -> {
                isAllValidFunc(PASSWORD, true)
                true
            }
        }
    }

    fun login() {
        viewModelScope.launch {

            repository.login(
                _textFieldMap[EMAIL],
                _textFieldMap[PASSWORD]
            )
                .collect {
                    when (it) {

                        is Resource.Loading -> {
                            _eventFlow.emit(LoginEvent.Loading)
                        }

                        is Resource.Success -> {
                            _eventFlow.emit(LoginEvent.LoginSuccess(it.data!!))
                        }

                        is Resource.Error -> {
                            _eventFlow.emit(LoginEvent.LoginFailed(it.data!!))
                        }
                    }
                }
        }
    }

    fun onSubmitClick() {
        if (checkValidEmail() && checkValidPassword()) {
            login()
        }
    }

    private fun isAllValidFunc(index: Int, value: Boolean) {
        _isFieldValid.value = Pair(index, value)
    }

    private val _textFieldMap: MutableList<String> = mutableListOf(
        PREFILLED_NULL,
        PREFILLED_NULL,
    )

    fun allFilled(values: MutableList<String>): Boolean {
        for (value in values) {
            if (value == PREFILLED_NULL || value.isBlank()) {
                return false
            }
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern
            .compile("^([a-zA-Z0-9_+\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,10})$")
            .matcher(email).matches()
    }

    companion object {
        const val PREFILLED_NULL = "NIL"
        const val EMAIL = 0
        const val PASSWORD = 1
        const val REQUIRED_FIELD = "Incorrect email or password"
        const val INVALID_EMAIL = "Invalid Email or incorrect format"
    }
}