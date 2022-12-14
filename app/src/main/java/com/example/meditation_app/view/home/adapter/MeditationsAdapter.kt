package com.example.meditation_app.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.databinding.ItemMeditationsBinding

class MeditationsAdapter: RecyclerView.Adapter<MeditationsAdapter.MyViewHolder>() {

    private lateinit var mListener: OnAdapterItemClickListener

    fun setOnItemClickListener(listener: OnAdapterItemClickListener) = listener.also { mListener = it }

    inner class MyViewHolder(val binding: ItemMeditationsBinding, listener: OnAdapterItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
            }
        }

    private val diffCallBack = object : DiffUtil.ItemCallback<Meditations>() {
        override fun areItemsTheSame(oldItem: Meditations, newItem: Meditations): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Meditations, newItem: Meditations): Boolean = newItem == oldItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var medList: List<Meditations>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemMeditationsBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListener)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMed = medList[position]
        holder.binding.apply {
            item = currentMed
            Glide.with(imageViewMed).load(currentMed.getImage()).into(imageViewMed)
        }
    }

    override fun getItemCount() = medList.size

}