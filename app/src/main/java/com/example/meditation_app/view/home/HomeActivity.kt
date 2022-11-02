package com.example.meditation_app.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityHomeBinding
import com.example.meditation_app.utils.FireStoreCollection.MED
import com.example.meditation_app.utils.FireStoreCollection.STORY
import com.example.meditation_app.utils.IntentConstants.OBJECT
import com.example.meditation_app.utils.IntentConstants.OBJECT_TYPE
import com.example.meditation_app.view.details.DetailsActivity
import com.example.meditation_app.view.home.adapter.MeditationsAdapter
import com.example.meditation_app.view.home.adapter.OnAdapterItemClickListener
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
            medAdapter.setOnItemClickListener(object : OnAdapterItemClickListener {
                override fun onItemClick(position: Int) {
                    Intent(this@HomeActivity, DetailsActivity::class.java).also {
                        it.putExtra(OBJECT_TYPE, MED)
                        it.putExtra(OBJECT, medAdapter.medList[position])
                        startActivity(it)
                    }
                }
            })
        }
        baseBinding.storyRecycler.apply {
            adapter = storyAdapter
            storyAdapter.setOnItemClickListener(object : OnAdapterItemClickListener {
                override fun onItemClick(position: Int) {
                    Intent(this@HomeActivity, DetailsActivity::class.java).also {
                        it.putExtra(OBJECT_TYPE, STORY)
                        it.putExtra(OBJECT, storyAdapter.storyList[position])
                        startActivity(it)
                    }
                }
            })
        }

        baseViewModel.currentUser.observe(this) { user ->
            if (user != null) baseBinding.cardView.visibility =
                if (user.vip == true) View.GONE else View.VISIBLE
        }
        baseViewModel.responseMed.observe(this) { if (it != null) medAdapter.medList = it }
        baseViewModel.responseStory.observe(this) { if (it != null) storyAdapter.storyList = it }

    }
}