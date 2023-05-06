package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.ResourceEntityType
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ResourceSectionToResourceEntityMapperTest {

    private lateinit var mapper: ResourceSectionToResourceEntityMapper

    @Before
    fun setUp() {
        mapper = ResourceSectionToResourceEntityMapper()
    }

    @Test
    fun `map should convert ResourceSection to List of ResourceEntity`() {
        val resourceSection = ResourceSection(
            categoryName = "Example Category",
            categoryType = SectionType.SEARCH_MIX,
            resources = listOf(
                Movie(
                    id = 1,
                    title = "Example Title",
                    overview = "Example Overview",
                    score = 90.0,
                    rating = 8.5,
                    posterImageUrl = "https://example.com/poster.jpg",
                    coverImageUrl = "https://example.com/cover.jpg",
                    isMovie = true
                )
            )
        )

        val expected = listOf(
            ResourceEntity(
                id = 1,
                title = "Example Title",
                overview = "Example Overview",
                score = 90.0,
                rating = 8.5,
                posterImageUrl = "https://example.com/poster.jpg",
                coverImageUrl = "https://example.com/cover.jpg",
                type = ResourceEntityType.MOVIE
            )
        )

        val result = mapper.map(resourceSection)

        assertEquals(expected, result)
    }
}