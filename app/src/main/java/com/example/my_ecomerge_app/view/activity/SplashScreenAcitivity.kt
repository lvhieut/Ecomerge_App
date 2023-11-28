package com.example.my_ecomerge_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.my_ecomerge_app.databinding.ActivitySplashScreenAcitivityBinding

class SplashScreenAcitivity : AppCompatActivity() {
    private val splashBinding by lazy { ActivitySplashScreenAcitivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(splashBinding.root)

        val splash = splashBinding.splashAnimation
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        splash.animate().translationX(2000f).setDuration(3000).setStartDelay(2900)


        Handler(Looper.getMainLooper()).postDelayed(Runnable{
            startActivity(Intent(
                this@SplashScreenAcitivity, LoginActivity::class.java
            )
        )
            finish()
        },5000)
    }
}