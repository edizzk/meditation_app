package com.example.meditation_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation_app.databinding.ActivityMainBinding
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.view.auth.LoginFragment
import com.example.meditation_app.view.auth.RegisterFragment
import com.example.meditation_app.view.auth.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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