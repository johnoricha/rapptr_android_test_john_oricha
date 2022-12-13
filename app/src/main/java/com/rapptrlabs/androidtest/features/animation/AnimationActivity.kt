package com.rapptrlabs.androidtest.features.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.rapptrlabs.androidtest.HomeActivity
import com.rapptrlabs.androidtest.databinding.ActivityAnimationBinding
import dagger.hilt.android.AndroidEntryPoint
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

@AndroidEntryPoint
class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Animation"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        binding.logo.setOnTouchListener(object : View.OnTouchListener {
            var x = 0f
            var y = 0f
            override fun onTouch(view: View, event: MotionEvent?): Boolean {

                when (event?.actionMasked) {

                    MotionEvent.ACTION_DOWN -> {

                        x = event.rawX
                        y = event.rawY

                    }
                    MotionEvent.ACTION_MOVE -> {

                        val dx = event.rawX - x
                        val dy = event.rawY - y

                        view.apply {
                            this.x = this.x + dx
                            this.y = this.y + dy
                        }

                        x = event.rawX
                        y = event.rawY
                    }
                    else -> {}
                }
                return true
            }

        })

        binding.btnFade.setOnClickListener {
            animateLogo()
            showConfetti()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun animateLogo() {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 3000
        fadeOut.startOffset = 1000
        fadeOut.fillAfter = true
        fadeOut.repeatCount = Animation.ABSOLUTE
        binding.logo.apply {
            startAnimation(fadeOut)
        }
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 3000
        fadeIn.fillAfter = true
        fadeIn.repeatCount = Animation.ABSOLUTE
        binding.logo.apply {
            startAnimation(fadeIn)

        }
    }

    private fun showConfetti() {
        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        )
        binding.konfettiView.start(party)
    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AnimationActivity::class.java)
            context.startActivity(intent)
        }
    }
}