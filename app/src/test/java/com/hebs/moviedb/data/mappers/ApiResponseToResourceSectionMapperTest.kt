package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.ResourceApiResponse
import com.hebs.moviedb.data.model.api.ResultsApiResponse
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ApiResponseToResourceSectionMapperTest {

    private lateinit var mapper: ApiResponseToResourceSectionMapper

    @Before
    fun setUp() {
        mapper = ApiResponseToResourceSectionMapper()
    }

    @Test
    fun `map should convert ResultsApiResponse to ResourceSection`() {
        val apiResponse = ResultsApiResponse(
            results = listOf(
                ResourceApiResponse(
                    id = 1,
                    title = "Example Title",
                    overview = "Example Overview",
                    rating = 8.5,
                    score = 90.0,
                    posterImageURL = "https://example.com/poster.jpg",
                    coverImageURL = "https://example.com/cover.jpg"
                )
            )
        )
        val categoryName = "Example Category"
        val categoryType = SectionType.POPULAR_MOVIES

        val expected = ResourceSection(
            resources = listOf(
                Movie(
                    id = 1,
                    title = "Example Title",
                    overview = "Example Overview",
                    rating = 8.5,
                    score = 90.0,
                    posterImageUrl = "https://example.com/poster.jpg",
                    coverImageUrl = "https://example.com/cover.jpg"
                )
            ),
            categoryName = categoryName,
            categoryType = categoryType
        )

        val result = mapper.map(apiResponse, categoryName, categoryType)

        assertEquals(expected, result)
    }
}