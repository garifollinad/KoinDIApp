package com.example.jsonholderapp.api

import com.example.jsonholderapp.models.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("posts")
    fun getPosts(): Single<List<Post>>

}