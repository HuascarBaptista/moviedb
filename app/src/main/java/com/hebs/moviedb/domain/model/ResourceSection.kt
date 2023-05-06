package com.hebs.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceSection(
    val categoryName: String,
    val categoryType: SectionType,
    val resources: List<Resource>,
) : Parcelable

enum class SectionType {
    POPULAR_MOVIES, POPULAR_TV_SHOWS, TOP_RATED_MOVIES, TOP_RATED_TV_SHOWS, SEARCH_MOVIES, SEARCH_TV_SHOW, SEARCH_MIX, BY_GENRE_MOVIES, BY_GENRE_TV_SHOWS;

    fun isMovieType(): Boolean {
        return when (this) {
            POPULAR_MOVIES, SEARCH_MOVIES, TOP_RATED_MOVIES, BY_GENRE_MOVIES -> true
            else -> false
        }
    }
}