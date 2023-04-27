package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.ResourceEntityType
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import javax.inject.Inject

class SectionToResourcesEntityMapper @Inject constructor() {
    fun map(resourceSection: ResourceSection): List<ResourceEntity> {
        return resourceSection.resources.map {
            ResourceEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                score = it.score,
                rating = it.rating,
                posterImageUrl = it.posterImageUrl,
                coverImageUrl = it.coverImageUrl,
                type = getResourceType(resourceSection.categoryType)
            )
        }
    }

    private fun getResourceType(type: SectionType) =
        if (type.isMovieType()) ResourceEntityType.MOVIE else ResourceEntityType.TV_SHOW
}