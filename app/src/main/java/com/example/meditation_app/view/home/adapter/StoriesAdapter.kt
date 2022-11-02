package com.example.meditation_app.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.databinding.ItemStoriesBinding

class StoriesAdapter: RecyclerView.Adapter<StoriesAdapter.MyViewHolder>() {

    private lateinit var mListener: OnAdapterItemClickListener

    fun setOnItemClickListener(listener: OnAdapterItemClickListener) = listener.also { mListener = it }

    inner class MyViewHolder(val binding: ItemStoriesBinding, listener: OnAdapterItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
            }
        }

    private val diffCallBack = object : DiffUtil.ItemCallback<Stories>() {
        override fun areItemsTheSame(oldItem: Stories, newItem: Stories): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Stories, newItem: Stories): Boolean = newItem == oldItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var storyList: List<Stories>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListener)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentStory = storyList[position]
        holder.binding.apply {
            titleTextStory.text = currentStory.name
            subtitleTextStory.text = currentStory.subtitle
        }
    }

    override fun getItemCount() = storyList.size

}