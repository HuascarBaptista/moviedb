package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.ResourceApiResponse
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.TvShow
import javax.inject.Inject

class ApiResponseToResourceSectionMapper @Inject constructor() {
    fun map(
        resources: ResultsApiResponse,
        categoryName: String,
        categoryType: SectionType,
    ): ResourceSection {
        return ResourceSection(
            resources = resources.results.map { getResourceByType(it, categoryType) },
            categoryType = categoryType,
            categoryName = categoryName
        )
    }

    private fun getResourceByType(
        resourceApiResponse: ResourceApiResponse,
        categoryType: SectionType
    ) =
        if (categoryType.isMovieType()) {
            Movie(
                id = resourceApiResponse.id,
                title = resourceApiResponse.title,
                overview = resourceApiResponse.overview,
                rating = resourceApiResponse.rating,
                score = resourceApiResponse.score,
                posterImageUrl = resourceApiResponse.posterImageURL,
                coverImageUrl = resourceApiResponse.coverImageURL
            )
        } else {
            TvShow(
                id = resourceApiResponse.id,
                title = resourceApiResponse.title,
                overview = resourceApiResponse.overview,
                rating = resourceApiResponse.rating,
                score = resourceApiResponse.score,
                posterImageUrl = resourceApiResponse.posterImageURL,
                coverImageUrl = resourceApiResponse.coverImageURL
            )
        }
}