package com.example.meditation_app.view.details

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.data.model.DetailViewObject
import com.example.meditation_app.databinding.ActivityDetailsBinding
import com.example.meditation_app.utils.FireStoreCollection.MED
import com.example.meditation_app.utils.IntentConstants.OBJECT
import com.example.meditation_app.utils.IntentConstants.OBJECT_TYPE
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.utils.dateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityDetailsBinding = ActivityDetailsBinding::inflate

    private val detailsViewModel: DetailsViewModel by viewModels()
    override fun getViewModel(): DetailsViewModel = detailsViewModel

    override fun setup() {
        val objectType = intent.getStringExtra(OBJECT_TYPE)
        val detailsObject = intent.getParcelableExtra(OBJECT) as DetailViewObject?

        baseBinding.apply {
            setSupportActionBar(myToolbar)
            myToolbar.setNavigationOnClickListener { onBackPressed() }
            titleText.text = detailsObject?.getName() ?: ""
            descText.text = detailsObject?.getDesc() ?: ""
            dateTimeText.text = dateFormatter()
            myToolbar.title = if (objectType == MED)
                UiString.StringResources(R.string.meditation_detail_title).asString(applicationContext)
            else
                UiString.StringResources(R.string.story_detail_title).asString(applicationContext)
        }

        playAudio()
    }

    private fun playAudio() {
        baseViewModel.mediaPlayer.observe(this) { state ->
            if (state != null) {
                baseBinding.apply {
                    imageCardDetail.setOnClickListener {
                        if (baseViewModel.mediaPlayer.value?.isPlaying == true) {
                            imageView.setImageResource(R.drawable.ic_baseline_play_arrow_30)
                            scrollView.background.alpha = 255
                            baseViewModel.mediaPlayer.value!!.pause()
                        } else {
                            imageView.setImageResource(R.drawable.ic_baseline_pause_30)
                            scrollView.background.alpha = 128
                            baseViewModel.mediaPlayer.value!!.start()
                        }
                    }
                }
            }
        }
    }
}