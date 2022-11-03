package com.example.meditation_app.view.details

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.data.model.DetailViewObject
import com.example.meditation_app.databinding.ActivityDetailsBinding
import com.example.meditation_app.utils.FireStoreCollection.MED
import com.example.meditation_app.utils.IntentConstants.OBJECT
import com.example.meditation_app.utils.IntentConstants.OBJECT_TYPE
import com.example.meditation_app.utils.Resource
import com.example.meditation_app.utils.UiString
import com.example.meditation_app.utils.dateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityDetailsBinding = ActivityDetailsBinding::inflate

    private val detailsViewModel: DetailsViewModel by viewModels()
    override fun getViewModel(): DetailsViewModel = detailsViewModel

    private var mediaPlayer: MediaPlayer? = null

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
        baseBinding.apply {
            imageCardDetail.setOnClickListener {
                if (mediaPlayer == null ) playAudio()
                else {
                    if (!mediaPlayer!!.isPlaying) {
                        imageView.setImageResource(R.drawable.ic_baseline_pause_30)
                        scrollView.background.alpha = 128
                        mediaPlayer!!.start()
                    } else {
                        imageView.setImageResource(R.drawable.ic_baseline_play_arrow_30)
                        scrollView.background.alpha = 255
                        mediaPlayer!!.pause()
                    }
                }
            }
        }
    }

    private fun playAudio() {
        baseBinding.imageView.visibility = View.INVISIBLE
        baseBinding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            baseViewModel.prepareMediaPlayer()
        }
        baseViewModel.mediaPlayer.observe(this) { state ->
            if (!state.isLoading) {
                baseBinding.progressBar.visibility = View.GONE
                baseBinding.imageView.visibility = View.VISIBLE
                when (state.data) {
                    is Resource.Success<*> -> {
                        mediaPlayer = state.data.data as MediaPlayer
                        if (!mediaPlayer!!.isPlaying) {
                            baseBinding.imageView.setImageResource(R.drawable.ic_baseline_pause_30)
                            baseBinding.scrollView.background.alpha = 128
                            mediaPlayer!!.start()
                        }
                    }
                    is Resource.Failure -> {
                        Toast.makeText(this@DetailsActivity, "${state.data.error}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.reset()
    }
}