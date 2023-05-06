package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.domain.model.ResourceSection
import javax.inject.Inject

class ResourceSectionToSectionEntityMapper @Inject constructor(private val sectionTypeMapper: SectionTypeMapper) {
    fun map(resourceSection: ResourceSection) =
        SectionEntity(
            categoryName = resourceSection.categoryName,
            categoryType = sectionTypeMapper.mapToEntity(resourceSection.categoryType)
        )
}