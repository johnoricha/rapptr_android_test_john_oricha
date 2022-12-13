package com.rapptrlabs.androidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rapptrlabs.androidtest.features.animation.AnimationActivity
import com.rapptrlabs.androidtest.features.chat.ui.ChatActivity
import com.rapptrlabs.androidtest.databinding.ActivityMainBinding
import com.rapptrlabs.androidtest.features.login.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main screen that lets you navigate to all other screens in the app.
 */

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.activity_main_title)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * =========================================================================================
         * INSTRUCTIONS
         * =========================================================================================
         *
         * 1. UI must work on Android phones of multiple sizes. Do not worry about Android Tablets.
         *
         * 2. Use this starter project as a base and build upon it. It is ok to remove some of the
         *    provided code if necessary.
         *
         * 3. Read the additional 'TODO' comments throughout the codebase, they will guide you.
         *
         * 3. Please take care of the bug(s) we left for you in the project as well.
         *
         * Thank you and Good luck. -  Rapptr Labs
         * =========================================================================================
         */

        // TODO: Make the UI look like it does in the mock-up
        // TODO: Add a ripple effect when the buttons are clicked

        binding.chatButton.setOnClickListener { ChatActivity.start(this) }

        binding.loginButton.setOnClickListener { LoginActivity.start(this) }

        binding.animationButton.setOnClickListener { AnimationActivity.start(this) }
    }
}