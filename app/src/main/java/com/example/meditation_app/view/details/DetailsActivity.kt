package com.example.meditation_app.view.details

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.meditation_app.R
import com.example.meditation_app.utils.FireStoreCollection.MED
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.databinding.ActivityDetailsBinding
import com.example.meditation_app.utils.IntentConstants.MED_OBJECT
import com.example.meditation_app.utils.IntentConstants.OBJECT_TYPE
import com.example.meditation_app.utils.IntentConstants.STORY_OBJECT
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.utils.dateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityDetailsBinding = ActivityDetailsBinding::inflate

    private val detailsViewModel: DetailsViewModel by viewModels()
    override fun getViewModel(): DetailsViewModel = detailsViewModel

    private var isActive: Boolean = false

    override fun setup() {
        baseBinding.apply {
            setSupportActionBar(myToolbar)
            myToolbar.setNavigationOnClickListener { onBackPressed() }
            if (intent.getStringExtra(OBJECT_TYPE) == MED) {
                myToolbar.title = UiString.StringResources(R.string.meditation_detail_title).asString(applicationContext)
                val detailsObject: Meditations? = intent.getParcelableExtra(MED_OBJECT)
                titleText.text = detailsObject?.name ?: ""
                descText.text = detailsObject?.desc ?: ""
            } else {
                myToolbar.title = UiString.StringResources(R.string.story_detail_title).asString(applicationContext)
                val detailsObject: Stories? = intent.getParcelableExtra(STORY_OBJECT)
                titleText.text = detailsObject?.name ?: ""
                descText.text = detailsObject?.desc ?: ""
            }
            dateTimeText.text = dateFormatter()
        }
        playAudio()
    }

    private fun playAudio() {
        baseViewModel.mediaPlayer.observe(this) { state ->
            if (state != null){
                baseBinding.apply {
                    imageCardDetail.setOnClickListener{
                        if (isActive) {
                            imageView.setImageResource(R.drawable.ic_baseline_play_arrow_30)
                            scrollView.background.alpha = 255
                            baseViewModel.mediaPlayer.value!!.pause()
                        }
                        else {
                            imageView.setImageResource(R.drawable.ic_baseline_pause_30)
                            scrollView.background.alpha = 128
                            baseViewModel.mediaPlayer.value!!.start()
                        }
                        isActive = !isActive
                    }
                }
            }
        }
    }
}