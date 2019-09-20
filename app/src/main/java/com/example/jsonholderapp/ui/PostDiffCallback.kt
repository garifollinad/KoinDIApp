package com.example.jsonholderapp.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.jsonholderapp.models.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

}