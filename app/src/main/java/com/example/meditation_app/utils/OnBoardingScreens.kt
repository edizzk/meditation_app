package com.example.meditation_app.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.meditation_app.R

sealed class OnBoardingScreens (
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    object First : OnBoardingScreens(
        image = R.drawable.onboarding1,
        title = R.string.on_boarding_first_title,
        description = R.string.on_boarding_first_desc
    )

    object Second : OnBoardingScreens(
        image = R.drawable.onboarding2,
        title = R.string.on_boarding_second_title,
        description = R.string.on_boarding_second_desc
    )

    object Third : OnBoardingScreens(
        image = R.drawable.onboarding3,
        title = R.string.on_boarding_third_title,
        description = R.string.on_boarding_third_desc
    )

    object Four : OnBoardingScreens(
        image = R.drawable.onboarding4,
        title = R.string.on_boarding_four_title,
        description = R.string.on_boarding_four_desc
    )
}

val onBoardingObjectList = listOf(
    OnBoardingScreens.First,
    OnBoardingScreens.Second,
    OnBoardingScreens.Third,
    OnBoardingScreens.Four
)