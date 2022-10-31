package com.example.meditation_app.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivitySplashBinding
import com.example.meditation_app.view.auth.AuthActivity
import com.example.meditation_app.view.home.HomeActivity
import com.example.meditation_app.view.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivitySplashBinding = ActivitySplashBinding::inflate

    @Inject lateinit var splashViewModel: SplashViewModel
    override fun getViewModel(): SplashViewModel = splashViewModel

    override fun setup() {
        baseViewModel.getOnBoardingStatePref { state ->
            if (state) Intent(this, OnBoardingActivity::class.java).also { startActivity(it) }
            else baseViewModel.getRememberMePref { state2 ->
                    if (state2 != null) Intent(this, HomeActivity::class.java).also { startActivity(it) }
                    else Intent(this, AuthActivity::class.java).also { startActivity(it) }
                }
            finish()
        }
    }
}