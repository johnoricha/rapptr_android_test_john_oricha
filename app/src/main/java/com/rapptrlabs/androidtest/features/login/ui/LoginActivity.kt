package com.rapptrlabs.androidtest.features.login.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rapptrlabs.androidtest.HomeActivity
import com.rapptrlabs.androidtest.R
import com.rapptrlabs.androidtest.databinding.ActivityLoginBinding
import com.rapptrlabs.androidtest.features.login.LoginEvent
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.EMAIL
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.INVALID_EMAIL
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.PASSWORD
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel.Companion.REQUIRED_FIELD
import com.rapptrlabs.androidtest.features.login.data.LoginResponseModel
import com.rapptrlabs.androidtest.features.login.viewmodel.LoginViewModel
import com.rapptrlabs.androidtest.util.Extensions.checkValidation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 */

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Login"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.enableSignInBtn.collect {
                    binding.loginBtn.isEnabled = it
                }

            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.isFieldValid.collect {

                    it?.let {
                        val id = it.first
                        val value = it.second


                        when (id) {
                            EMAIL -> {
                                binding.loginEmailTI.checkValidation(value, INVALID_EMAIL)
                            }
                            PASSWORD -> {
                                binding.loginPasswordTI.checkValidation(value, REQUIRED_FIELD)
                            }
                        }
                    }

                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.eventFlow.collect {event ->
                    when (event) {

                        is LoginEvent.Loading -> {
                            binding.loginProgressBar.visibility = View.VISIBLE
                        }

                        is LoginEvent.LoginSuccess -> {
                            showLoginSuccessDialog(event.data)
                            binding.loginProgressBar.visibility = View.INVISIBLE
                        }

                        is LoginEvent.LoginFailed -> {
                            showLoginFailureDialog(event.data)
                            binding.loginProgressBar.visibility = View.INVISIBLE
                        }
                    }
                }

            }


        }

        onClicks()

    }



    private fun showLoginSuccessDialog(loginResult: LoginResponseModel) {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.apply {
            setTitle(loginResult.code)
            setMessage("${loginResult.message} \nTook ${loginResult.callDuration} ms")
            setPositiveButton("Ok") { _: DialogInterface?, _: Int ->
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finishAffinity()
            }

        }.create().show()
    }

    private fun showLoginFailureDialog(loginResponse: LoginResponseModel) {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.apply {
            setTitle(loginResponse.code)
            setMessage(loginResponse.message)
            setPositiveButton("Ok") { _: DialogInterface?, _: Int ->
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finishAffinity()
            }
        }.create().show()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun onClicks() {
        binding.loginEmailET.addTextChangedListener(this)
        binding.loginPasswordET.addTextChangedListener(this)
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginBtn -> loginViewModel.onSubmitClick()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        when (s.toString()) {
            binding.loginEmailET.editableText?.toString() -> {
                loginViewModel.setTextFieldValue(EMAIL, s.toString())
            }
            binding.loginPasswordET.editableText?.toString() -> {
                loginViewModel.setTextFieldValue(PASSWORD, s.toString())
            }
        }
    }
}