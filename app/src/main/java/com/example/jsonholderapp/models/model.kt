package com.example.jsonholderapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "posts")
data class Post (
    @SerializedName("userId") val userId : Int,
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("body") val body : String
): Serializable