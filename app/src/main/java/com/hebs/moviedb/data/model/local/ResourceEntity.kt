package com.hebs.moviedb.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resource")
data class ResourceEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String?,
    val score: Double,
    val rating: Double,
    val posterImageUrl: String?,
    val coverImageUrl: String?,
    val type: ResourceEntityType
)

enum class ResourceEntityType {
    MOVIE,
    TV_SHOW;

    fun isMovieType() = this == MOVIE
}
