package com.hebs.moviedb.domain.model

data class ResourceSection(
    val categoryName: String,
    val categoryType: SectionType,
    val resources: List<Resource>,
) {
    fun isValid() = resources.isNotEmpty()
}

enum class SectionType {
    POPULAR_MOVIES, POPULAR_TV_SHOWS, TOP_RATED_MOVIES, TOP_RATED_TV_SHOWS, SEARCH_MOVIES, SEARCH_TV_SHOWS;

    fun isMovieType(): Boolean {
        return when (this) {
            POPULAR_MOVIES, TOP_RATED_MOVIES, SEARCH_MOVIES -> true
            else -> false
        }
    }
}