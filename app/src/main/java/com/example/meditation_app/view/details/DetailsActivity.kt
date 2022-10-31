package com.example.meditation_app.view.details

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityDetailsBinding = ActivityDetailsBinding::inflate

    private val detailsViewModel: DetailsViewModel by viewModels()
    override fun getViewModel(): DetailsViewModel = detailsViewModel

    override fun setup() {
        baseBinding.apply {
            setSupportActionBar(myToolbar)
            myToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }


    }

}