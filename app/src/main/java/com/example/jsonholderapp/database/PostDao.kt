package com.example.jsonholderapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jsonholderapp.models.Post
import io.reactivex.Single
import androidx.paging.DataSource

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>)

    @Query("select * from posts")
    fun getPosts(): Single<List<Post>>

    @Query("select * from posts")
    fun getPagedPosts(): DataSource.Factory<Int, Post>
}