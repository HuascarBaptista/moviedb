package com.hebs.moviedb.data.model.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GenresWithResources(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = GenreResourceCrossEntity::class,
            parentColumn = "genreId",
            entityColumn = "resourceId"
        )
    )
    val resources: List<ResourceEntity>
)