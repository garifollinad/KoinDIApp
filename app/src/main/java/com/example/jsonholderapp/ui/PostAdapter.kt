package com.example.jsonholderapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonholderapp.R
import com.example.jsonholderapp.models.Post
import kotlinx.android.synthetic.main.item_post.view.*
import androidx.paging.PagedList


class PostAdapter(val context: Context) : PagedListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holderPost: PostViewHolder, position: Int) {
        var post = getItem(position)

        if (post == null) {
            holderPost.clear()
        } else {
            holderPost.bind(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_post,
            parent, false))
    }

    class PostViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.namePost

        fun bind(post: Post) {
            tvName.text = post.toString()
            Log.d("repository_pagsss", post.toString())
        }

        fun clear() {
            tvName.text = null
        }

    }


}