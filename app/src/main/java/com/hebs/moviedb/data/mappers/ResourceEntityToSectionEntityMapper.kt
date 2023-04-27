package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.ResourceEntityResponse
import com.hebs.moviedb.data.model.SectionEntity
import com.hebs.moviedb.data.model.SectionEntityType
import javax.inject.Inject

class ResourceEntityToSectionEntityMapper @Inject constructor() {
    fun fromEntity(
        entity: ResourceEntityResponse,
        categoryName: String,
        categoryType: SectionEntityType
    ): SectionEntity {
        return SectionEntity(
            resources = entity.results,
            categoryType = categoryType,
            categoryName = categoryName
        )
    }
}