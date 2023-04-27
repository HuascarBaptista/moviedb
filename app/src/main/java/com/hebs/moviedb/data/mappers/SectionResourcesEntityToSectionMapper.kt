package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.TvShow
import javax.inject.Inject

class SectionResourcesEntityToSectionMapper @Inject constructor(private val categoryTypeMapper: CategoryTypeMapper) {
    fun map(sectionEntity: SectionEntity, resourcesEntity: List<ResourceEntity>): ResourceSection {
        val categoryType = categoryTypeMapper.mapFromEntity(sectionEntity.categoryType)

        val resources = if (categoryType.isMovieType()) {
            getMovieResources(resourcesEntity)
        } else {
            getTvShowResources(resourcesEntity)
        }
        return ResourceSection(
            categoryName = sectionEntity.categoryName,
            categoryType = categoryType,
            resources = resources
        )
    }

    private fun getMovieResources(resources: List<ResourceEntity>): List<Resource> {
        return resources.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                score = it.score,
                rating = it.rating,
                posterImageUrl = it.posterImageUrl,
                coverImageUrl = it.coverImageUrl
            )
        }
    }

    private fun getTvShowResources(resources: List<ResourceEntity>): List<Resource> {
        return resources.map {
            TvShow(
                id = it.id,
                title = it.title,
                overview = it.overview,
                score = it.score,
                rating = it.rating,
                posterImageUrl = it.posterImageUrl,
                coverImageUrl = it.coverImageUrl
            )
        }
    }
}
