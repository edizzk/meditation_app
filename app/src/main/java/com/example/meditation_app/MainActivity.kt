package com.example.meditation_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meditation_app.databinding.ActivityMainBinding
import com.example.meditation_app.view.login_register.LoginFragment
import com.example.meditation_app.view.login_register.RegisterFragment
import com.example.meditation_app.view.login_register.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}