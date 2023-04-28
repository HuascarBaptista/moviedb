package com.hebs.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceSection(
    val categoryName: String,
    val categoryType: SectionType,
    val resources: List<Resource>,
) : Parcelable {
    fun isValid() = resources.isNotEmpty()
}

enum class SectionType {
    POPULAR_MOVIES, POPULAR_TV_SHOWS, TOP_RATED_MOVIES, TOP_RATED_TV_SHOWS, SEARCH;

    fun isMovieType(): Boolean {
        return when (this) {
            POPULAR_MOVIES, TOP_RATED_MOVIES -> true
            else -> false
        }
    }
}