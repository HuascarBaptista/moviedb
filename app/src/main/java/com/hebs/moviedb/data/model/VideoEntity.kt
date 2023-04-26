package com.hebs.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class VideoEntity(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("published_at") val publishedAt: String,
    val resourceId: Int
)