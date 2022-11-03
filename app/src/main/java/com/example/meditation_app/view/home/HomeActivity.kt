package com.example.meditation_app.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.databinding.ActivityHomeBinding
import com.example.meditation_app.utils.FireStoreCollection.MED
import com.example.meditation_app.utils.FireStoreCollection.STORY
import com.example.meditation_app.utils.IntentConstants.OBJECT
import com.example.meditation_app.utils.IntentConstants.OBJECT_TYPE
import com.example.meditation_app.utils.Resource
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

        baseViewModel.apply {
            currentUser.observe(this@HomeActivity) { user ->
                if (user != null) baseBinding.bannerView.visibility =
                    if (user.vip == true) View.GONE else View.VISIBLE
            }
            stateMed.observe(this@HomeActivity) { state ->
                if(!state.isLoading) {
                    when (state.data) {
                        is Resource.Success<*> -> {
                            baseBinding.medRecyclerProgressBar.visibility = View.GONE
                            baseBinding.medRecycler.visibility = View.VISIBLE
                            medAdapter.medList = state.data.data as List<Meditations>
                        }
                        is Resource.Failure -> {
                            Toast.makeText(this@HomeActivity, "${state.data.error}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            stateStory.observe(this@HomeActivity) { state ->
                if(!state.isLoading) {
                    when (state.data) {
                        is Resource.Success<*> -> {
                            baseBinding.storyRecyclerProgressBar.visibility = View.GONE
                            baseBinding.storyRecycler.visibility = View.VISIBLE
                            storyAdapter.storyList = state.data.data as List<Stories>
                        }
                        is Resource.Failure -> {
                            Toast.makeText(this@HomeActivity, "${state.data.error}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}