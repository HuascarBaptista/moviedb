package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.TvShow
import javax.inject.Inject

class SectionEntityToResourceSectionMapper @Inject constructor(private val sectionTypeMapper: SectionTypeMapper) {
    fun map(sectionEntity: SectionEntity, resourcesEntity: List<ResourceEntity>): ResourceSection {
        val categoryType = sectionTypeMapper.mapFromEntity(sectionEntity.categoryType)

        val resources = resourcesEntity.map {
            if (it.type.isMovieType()) {
                getMovieResource(it)
            } else {
                getTvShowResource(it)
            }
        }
        return ResourceSection(
            categoryName = sectionEntity.categoryName,
            categoryType = categoryType,
            resources = resources
        )
    }

    private fun getMovieResource(resource: ResourceEntity) =
        Movie(
            id = resource.id,
            title = resource.title,
            overview = resource.overview,
            score = resource.score,
            rating = resource.rating,
            posterImageUrl = resource.posterImageUrl,
            coverImageUrl = resource.coverImageUrl
        )

    private fun getTvShowResource(resource: ResourceEntity) =
        TvShow(
            id = resource.id,
            title = resource.title,
            overview = resource.overview,
            score = resource.score,
            rating = resource.rating,
            posterImageUrl = resource.posterImageUrl,
            coverImageUrl = resource.coverImageUrl
        )
}
