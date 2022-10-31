package com.example.meditation_app.view.auth

import android.view.LayoutInflater
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityAuthBinding
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.view.auth.adapter.ViewPagerAdapter
import com.example.meditation_app.view.auth.login.LoginFragment
import com.example.meditation_app.view.auth.register.RegisterFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityAuthBinding = ActivityAuthBinding::inflate

    @Inject lateinit var authViewModel: AuthViewModel
    override fun getViewModel(): AuthViewModel = authViewModel

    override fun setup() {
        val fragmentList = listOf(RegisterFragment.create(), LoginFragment.create())
        val fragmentTitleList = listOf(
            UiString.StringResources(R.string.register).asString(applicationContext),
            UiString.StringResources(R.string.login).asString(applicationContext)
        )

        val adapter = ViewPagerAdapter(this, fragmentList)
        baseBinding.viewPager2.adapter = adapter
        TabLayoutMediator(baseBinding.tabLayout, baseBinding.viewPager2){
                tab, pos -> tab.text = fragmentTitleList[pos]
        }.attach()
    }

}