package com.hebs.moviedb.data.model.local

import androidx.room.Entity

@Entity(
    tableName = "genre_resource", primaryKeys = ["genreId", "resourceId"]
)
data class GenreResourceCrossEntity(
    val genreId: String, val resourceId: Int
)