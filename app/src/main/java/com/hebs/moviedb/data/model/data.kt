package com.hebs.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class ResourceEntityResponse(
    @SerializedName("results") val results: List<ResourceEntity>
)