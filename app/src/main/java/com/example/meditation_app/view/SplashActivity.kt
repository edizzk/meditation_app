package com.example.meditation_app.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation_app.view.auth.AuthActivity
import com.example.meditation_app.view.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject lateinit var splashViewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.getOnBoardingStatePref { state ->
            if (state) Intent(this, OnBoardingActivity::class.java).also { startActivity(it) }
            else Intent(this, AuthActivity::class.java).also { startActivity(it) }
            finish()
        }
    }
}