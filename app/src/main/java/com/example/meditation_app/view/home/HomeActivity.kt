package com.example.meditation_app.view.home

import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityHomeBinding
import com.example.meditation_app.view.home.adapter.MeditationsAdapter
import com.example.meditation_app.view.home.adapter.StoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewModel(): HomeViewModel = homeViewModel

    private lateinit var medAdapter: MeditationsAdapter
    private lateinit var storyAdapter: StoriesAdapter

    override fun setup() {
        medAdapter = MeditationsAdapter()
        storyAdapter = StoriesAdapter()

        baseBinding.medRecycler.apply {
            adapter = medAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        baseBinding.storyRecycler.apply {
            adapter = storyAdapter
            layoutManager = GridLayoutManager(this@HomeActivity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        baseViewModel.currentUser.observe(this) { user ->
            if (user != null) baseBinding.cardView.visibility = if (user.vip == true) View.GONE else View.VISIBLE
        }
        baseViewModel.responseMed.observe(this) { if (it != null) medAdapter.medList = it }
        baseViewModel.responseStory.observe(this) { if (it != null) storyAdapter.storyList = it }

    }
}