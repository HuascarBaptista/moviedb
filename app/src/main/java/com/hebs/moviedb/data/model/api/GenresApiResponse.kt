package com.hebs.moviedb.data.model.api

import com.google.gson.annotations.SerializedName

data class GenresListApiResponse(
    @SerializedName("genres") val results: List<GenresApiResponse>
)

data class GenresApiResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)