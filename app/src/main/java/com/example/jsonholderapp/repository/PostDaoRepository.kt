package com.example.jsonholderapp.repository

import android.util.Log
import com.example.jsonholderapp.api.PostApi
import com.example.jsonholderapp.database.Database
import com.example.jsonholderapp.models.Post
import io.reactivex.Single

interface PostDaoRepository {
    fun getLocalDaoPosts() : Single<List<Post>>
    fun getDaoPosts() : Single<List<Post>>
    fun insertPosts(posts: List<Post>)
}

class PostDaoRepositoryImpl(val database: Database, val api: PostApi) : PostDaoRepository {

    override fun getLocalDaoPosts(): Single<List<Post>> {
        return database.postDao().getPosts()
    }

    override fun insertPosts(posts: List<Post>) {
        database.postDao().insertPosts(posts)
    }

    override fun getDaoPosts() : Single<List<Post>> {
        return api.getPosts()
            .map { list ->
                Log.d("posts_log", list.toString())
                list
            }
    }


}