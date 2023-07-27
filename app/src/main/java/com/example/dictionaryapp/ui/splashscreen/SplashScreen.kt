package com.example.dictionaryapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.SplashScreenActivityBinding
import com.example.dictionaryapp.ui.MainActivity

class SplashScreen : AppCompatActivity() {

    private val binding by lazy {
        SplashScreenActivityBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding.byElshodText.animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }
}