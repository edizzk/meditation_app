package com.example.meditation_app.view.onboarding

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.meditation_app.R
import com.example.meditation_app.base.BaseActivity
import com.example.meditation_app.databinding.ActivityOnBoardingBinding
import com.example.meditation_app.utils.onBoardingObjectList
import com.example.meditation_app.view.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding, OnBoardingViewModel>() {

    override fun getInflater(): (LayoutInflater) -> ActivityOnBoardingBinding = ActivityOnBoardingBinding::inflate

    @Inject lateinit var onBoardingViewModel: OnBoardingViewModel
    override fun getViewModel(): OnBoardingViewModel = onBoardingViewModel

    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun setup() {
        initViewPager()
        baseBinding.cardOnBoarding.setOnClickListener {
            baseViewModel.saveOnBoardingStatePref(false)
            Intent(applicationContext, AuthActivity::class.java).apply {startActivity(this)}
        }
    }

    private fun initViewPager() {
        onBoardingAdapter = OnBoardingAdapter(onBoardingObjectList)
        baseBinding.apply {
            onBoardingViewPager.adapter = onBoardingAdapter
            setupOnBoardingIndicators()
            setCurrentOnBoardingIndicator(0)
            onBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentOnBoardingIndicator(position)
                }
            })
        }
    }

    private fun setupOnBoardingIndicators() {
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (n in onBoardingObjectList.indices){
            val indicator = ImageView(applicationContext)
            indicator.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.on_boarding_indicator_inactive))
            indicator.layoutParams = layoutParams
            baseBinding.layoutOnBoardingIndicators.addView(indicator)
        }
    }

    private fun setCurrentOnBoardingIndicator(index: Int){
        val childCount = baseBinding.layoutOnBoardingIndicators.childCount
        for (n in 0 until childCount){
            val imageView = baseBinding.layoutOnBoardingIndicators.getChildAt(n) as ImageView
            if(n == index) imageView.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.on_boarding_indicator_active)
            ) else imageView.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.on_boarding_indicator_inactive)
            )
        }
    }
}