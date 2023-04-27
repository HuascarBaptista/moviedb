package com.hebs.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class SectionEntity(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val categoryName: String?,
    @SerializedName("type") val categoryType: SectionEntityType,
    @SerializedName("resources") val resources: List<ResourceEntity>? = null
)

enum class SectionEntityType {
    POPULAR_MOVIES,
    POPULAR_TV_SHOWS,
    TOP_RATED_MOVIES,
    TOP_RATED_TV_SHOWS,
    SEARCH
}