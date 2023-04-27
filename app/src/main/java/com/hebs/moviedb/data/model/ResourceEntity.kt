package com.hebs.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class ResourceEntity(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "title", alternate = ["name"]) val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("poster_path") val posterImageURL: String?,
    @SerializedName("backdrop_path") val coverImageURL: String?
)