package com.rapptrlabs.androidtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable
            { //This method will be executed once the timer is over
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }, 3000
        )
    }
}