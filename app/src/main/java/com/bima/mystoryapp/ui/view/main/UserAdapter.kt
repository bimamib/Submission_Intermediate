package com.bima.mystoryapp.ui.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bima.mystoryapp.data.response.ListStoryItem
import com.bima.mystoryapp.databinding.ItemStoryuserBinding
import com.bima.mystoryapp.ui.view.detail.DetailActivity
import com.bumptech.glide.Glide

class UserAdapter : PagingDataAdapter<ListStoryItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemStoryuserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    inner class MyViewHolder(private val binding: ItemStoryuserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: ListStoryItem) {
            binding.tvItemName.text = itemName.name
            Glide.with(binding.root)
                .load(itemName.photoUrl)
                .into(binding.ivItemPhoto)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.ID, itemName.id)
                intentDetail.putExtra(DetailActivity.NAME, itemName.name)
                intentDetail.putExtra(DetailActivity.DESCRIPTION, itemName.description)
                intentDetail.putExtra(DetailActivity.PICTURE, itemName.photoUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}