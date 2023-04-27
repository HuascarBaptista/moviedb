package com.hebs.moviedb.data.model.local

import androidx.room.Entity

@Entity(
    tableName = "section_resource", primaryKeys = ["categoryName", "resourceId"]
)
data class SectionResourceCrossEntity(
    val categoryName: String, val resourceId: Int
)