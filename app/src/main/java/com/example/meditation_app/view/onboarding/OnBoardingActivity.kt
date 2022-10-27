package com.example.meditation_app.view.onboarding

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.meditation_app.R
import com.example.meditation_app.databinding.ActivityOnBoardingBinding
import com.example.meditation_app.utils.onBoardingObjectList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    private lateinit var onBoardingAdapter: OnBoardingAdapter
    @Inject lateinit var onBoardingViewModel: OnBoardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViewPager()

    }

    private fun initViewPager() {
        onBoardingAdapter = OnBoardingAdapter(onBoardingObjectList)
        binding.apply {
            onBoardingViewPager.adapter = onBoardingAdapter
        }
    }

}