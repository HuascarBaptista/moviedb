package com.hebs.moviedb.domain.model

data class ResourceSection(
    val id: Int? = null,
    val categoryName: String,
    val categoryType: SectionType,
    val resources: List<Resource>,
) {
    fun isValid() = resources.isNotEmpty()
}

enum class SectionType {
    POPULAR_MOVIES, POPULAR_TV_SHOWS, TOP_RATED_MOVIES, TOP_RATED_TV_SHOWS, SEARCH
}