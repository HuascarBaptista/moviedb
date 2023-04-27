package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.SectionEntity
import com.hebs.moviedb.data.model.SectionEntityType
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.TvShow
import javax.inject.Inject

class SectionEntityToResourceSectionMapper @Inject constructor() {
    fun fromEntity(sectionEntity: SectionEntity): ResourceSection {
        val categoryType = when (sectionEntity.categoryType) {
            SectionEntityType.POPULAR_MOVIES -> SectionType.POPULAR_MOVIES
            SectionEntityType.POPULAR_TV_SHOWS -> SectionType.POPULAR_TV_SHOWS
            SectionEntityType.TOP_RATED_MOVIES -> SectionType.TOP_RATED_MOVIES
            SectionEntityType.TOP_RATED_TV_SHOWS -> SectionType.TOP_RATED_TV_SHOWS
            SectionEntityType.SEARCH -> SectionType.SEARCH
        }

        val resources = when (categoryType) {
            SectionType.POPULAR_MOVIES, SectionType.TOP_RATED_MOVIES -> getMovieResources(
                sectionEntity
            )
            SectionType.POPULAR_TV_SHOWS, SectionType.TOP_RATED_TV_SHOWS, SectionType.SEARCH -> getTvShowResources(
                sectionEntity
            )
        }

        return ResourceSection(
            categoryName = sectionEntity.categoryName.orEmpty(),
            categoryType = categoryType,
            resources = resources.orEmpty()
        )
    }

    private fun getMovieResources(sectionEntity: SectionEntity): List<Resource>? {
        return sectionEntity.resources?.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                rating = it.rating,
                posterImageURL = it.posterImageURL,
                coverImageURL = it.coverImageURL
            )
        }
    }

    private fun getTvShowResources(sectionEntity: SectionEntity): List<Resource>? {
        return sectionEntity.resources?.map {
            TvShow(
                id = it.id,
                title = it.title,
                overview = it.overview,
                rating = it.rating,
                posterImageURL = it.posterImageURL,
                coverImageURL = it.coverImageURL
            )
        }
    }
}