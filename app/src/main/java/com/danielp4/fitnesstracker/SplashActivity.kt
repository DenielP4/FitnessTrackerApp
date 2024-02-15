package com.danielp4.fitnesstracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.airbnb.lottie.LottieDrawable
import com.danielp4.fitnesstracker.databinding.SplashMainBinding


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private lateinit var binding: SplashMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lottieView.apply {
            setMinProgress(0.0f)
            setMaxProgress(1.0f)
            repeatCount = LottieDrawable.INFINITE
            repeatMode = LottieDrawable.REVERSE
            playAnimation()
        }
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                binding.lottieView.pauseAnimation()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}