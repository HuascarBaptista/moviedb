package com.hebs.moviedb.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "section")
data class SectionEntity(
    @PrimaryKey
    val categoryName: String,
    val categoryType: SectionEntityType,
)

enum class SectionEntityType {
    POPULAR_MOVIES,
    POPULAR_TV_SHOWS,
    TOP_RATED_MOVIES,
    TOP_RATED_TV_SHOWS,
    BY_GENRE_MOVIES,
    BY_GENRE_TV_SHOWS,
    SEARCH;

    fun isPopularType(): Boolean {
        return when (this) {
            POPULAR_MOVIES, POPULAR_TV_SHOWS -> true
            else -> false
        }
    }

    fun isTopRatedType(): Boolean {
        return when (this) {
            TOP_RATED_TV_SHOWS, TOP_RATED_MOVIES -> true
            else -> false
        }
    }

    fun isSearchType(): Boolean {
        return when (this) {
            SEARCH -> true
            else -> false
        }
    }

    fun isGenreType(): Boolean {
        return when (this) {
            BY_GENRE_MOVIES, BY_GENRE_TV_SHOWS -> true
            else -> false
        }
    }

}
