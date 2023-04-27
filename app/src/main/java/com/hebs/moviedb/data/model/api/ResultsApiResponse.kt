package com.hebs.moviedb.data.model.api

import com.google.gson.annotations.SerializedName

data class ResultsApiResponse(
    @SerializedName("results") val results: List<ResourceApiResponse>
)