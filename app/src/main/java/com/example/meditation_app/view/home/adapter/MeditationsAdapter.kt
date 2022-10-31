package com.example.meditation_app.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.databinding.ItemMeditationsBinding

class MeditationsAdapter: RecyclerView.Adapter<MeditationsAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMeditationsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Meditations>() {
        override fun areItemsTheSame(oldItem: Meditations, newItem: Meditations): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Meditations, newItem: Meditations): Boolean = newItem == oldItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var medList: List<Meditations>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemMeditationsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMed = medList[position]
        holder.binding.apply {
            titleTextMed.text = currentMed.name
            subtitleTextMed.text = currentMed.subtitle
        }
    }

    override fun getItemCount() = medList.size

}