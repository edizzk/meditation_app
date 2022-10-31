package com.example.meditation_app.view.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation_app.R
import com.example.meditation_app.utils.OnBoardingScreens

class OnBoardingAdapter(private val listOfOnBoarding: List<OnBoardingScreens>)
    : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_container_on_boarding, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfOnBoarding.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bindItems(listOfOnBoarding[position])
    }

    class OnBoardingViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        fun bindItems(itemModel : OnBoardingScreens) {
            val image: ImageView = itemView.findViewById(R.id.mainImage)
            val title: TextView = itemView.findViewById(R.id.titleText)
            val description: TextView = itemView.findViewById(R.id.descriptionText)

            image.setImageResource(itemModel.image)
            title.setText(itemModel.title)
            description.setText(itemModel.description)
        }
    }

}