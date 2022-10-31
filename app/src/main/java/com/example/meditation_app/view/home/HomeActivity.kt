package com.example.meditation_app.view.home

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityHomeBinding
import com.example.meditation_app.view.home.adapter.MeditationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewModel(): HomeViewModel = homeViewModel

    private lateinit var medAdapter: MeditationsAdapter

    override fun setup() {
        medAdapter = MeditationsAdapter()

        baseBinding.medRecycler.apply {
            adapter = medAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

    }
}