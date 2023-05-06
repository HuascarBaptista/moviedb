package com.hebs.moviedb.data.model.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SectionWithResources(
    @Embedded val section: SectionEntity,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "id",
        associateBy = Junction(
            value = SectionResourceCrossEntity::class,
            parentColumn = "categoryName",
            entityColumn = "resourceId"
        )
    )
    val resources: List<ResourceEntity>
)