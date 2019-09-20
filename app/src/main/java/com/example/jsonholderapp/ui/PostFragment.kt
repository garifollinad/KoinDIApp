package com.example.jsonholderapp.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jsonholderapp.R
import com.example.jsonholderapp.models.Post
import org.koin.android.ext.android.inject

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by inject()
    private var posts: ArrayList<Post>? = null
    private var postTv: TextView ?= null
    var adapter: PostAdapter? = null
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            bindViews(view)
            setData()
            setAdapter()
        }
    }

    private fun bindViews(view: View) = with(view) {
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun setAdapter() {
        adapter = PostAdapter(context!!)
        layoutManager = LinearLayoutManager(getActivity())
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: PostAdapter?) {

        viewModel.getPagedLocalPost().observe(this, Observer { names ->
            adapter?.submitList(names)
            Log.d("repository_paged",names.toString() )
        })

    }

    private fun setData() {
        viewModel.getPost()
        viewModel.getLocalPost()
        viewModel.liveData.observe(this, Observer {
            when(it) {
                is PostViewModel.PostData.HideLoading -> {

                }
                is PostViewModel.PostData.ShowLoading -> {

                }
                is PostViewModel.PostData.Error -> {
                }
                is PostViewModel.PostData.PostResult -> {
//                    posts = it.posts
//                    Log.d("posts_log", posts.toString())
//                    postTv?.text = posts.toString()
                }
                is PostViewModel.PostData.PostLocalResult -> {
//                    posts = it.postsLocal
//                    Log.d("posts_local", posts.toString())
//                    postTv?.text = posts.toString()
                }
            }
        })
    }

}
