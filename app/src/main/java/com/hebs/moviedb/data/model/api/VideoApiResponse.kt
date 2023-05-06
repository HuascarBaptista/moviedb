package com.hebs.moviedb.data.model.api

import com.google.gson.annotations.SerializedName

data class ResultVideoApiResponse(
    @SerializedName("results") val results: List<VideoApiResponse>
)

data class VideoApiResponse(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("published_at") val publishedAt: String,
    val resourceId: Int
)