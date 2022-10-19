package com.example.meditation_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meditation_app.databinding.ActivityMainBinding
import com.example.meditation_app.view.auth.LoginFragment
import com.example.meditation_app.view.auth.RegisterFragment
import com.example.meditation_app.view.auth.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val fragmentList = listOf(
        RegisterFragment.create(),
        LoginFragment.create()
    )
    private val fragmentTitleList = listOf(
        "ÜYE OL",
        "GİRİŞ YAP"
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = ViewPagerAdapter(this, fragmentList)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2){
            tab, pos -> tab.text = fragmentTitleList[pos]
        }.attach()

    }
}