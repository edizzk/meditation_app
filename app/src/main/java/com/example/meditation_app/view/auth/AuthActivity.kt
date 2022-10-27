package com.example.meditation_app.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meditation_app.R
import com.example.meditation_app.databinding.ActivityAuthBinding
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.view.auth.login.LoginFragment
import com.example.meditation_app.view.auth.register.RegisterFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createAdapter()
    }

    private fun createAdapter() {
        val fragmentList = listOf(
            RegisterFragment.create(),
            LoginFragment.create()
        )
        val fragmentTitleList = listOf(
            UiString.StringResources(R.string.register).asString(applicationContext),
            UiString.StringResources(R.string.login).asString(applicationContext)
        )

        val adapter = ViewPagerAdapter(this, fragmentList)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2){
                tab, pos -> tab.text = fragmentTitleList[pos]
        }.attach()
    }
}