package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.domain.model.ResourceSection
import javax.inject.Inject

class SectionToSectionEntityMapper @Inject constructor(private val categoryTypeMapper: CategoryTypeMapper) {
    fun map(resourceSection: ResourceSection) =
        SectionEntity(
            categoryName = resourceSection.categoryName,
            categoryType = categoryTypeMapper.mapToEntity(resourceSection.categoryType)
        )
}