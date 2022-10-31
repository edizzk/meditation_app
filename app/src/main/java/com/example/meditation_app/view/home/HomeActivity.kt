package com.example.meditation_app.view.home

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewModel(): HomeViewModel = homeViewModel

    override fun setup() {

    }
}