package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.ResourceEntityType
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SectionEntityToResourceSectionMapperTest {

    private lateinit var mapper: SectionEntityToResourceSectionMapper
    private lateinit var sectionTypeMapper: SectionTypeMapper

    @Before
    fun setUp() {
        sectionTypeMapper = SectionTypeMapper()
        mapper = SectionEntityToResourceSectionMapper(sectionTypeMapper)
    }

    @Test
    fun `map should convert SectionEntity and List of ResourceEntity to ResourceSection`() {
        val sectionEntity = SectionEntity(
            categoryName = "Example Category",
            categoryType = SectionEntityType.POPULAR_MOVIES
        )
        val resourcesEntity = listOf(
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

        val expected = ResourceSection(
            categoryName = "Example Category",
            categoryType = SectionType.POPULAR_MOVIES,
            resources = listOf(
                Movie(
                    id = 1,
                    title = "Example Title",
                    overview = "Example Overview",
                    score = 90.0,
                    rating = 8.5,
                    posterImageUrl = "https://example.com/poster.jpg",
                    coverImageUrl = "https://example.com/cover.jpg"
                )
            )
        )

        val result = mapper.map(sectionEntity, resourcesEntity)

        assertEquals(expected, result)
    }
}