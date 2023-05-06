package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ResourceSectionToSectionEntityMapperTest {

    private lateinit var mapper: ResourceSectionToSectionEntityMapper
    private lateinit var sectionTypeMapper: SectionTypeMapper

    @Before
    fun setUp() {
        sectionTypeMapper = SectionTypeMapper()
        mapper = ResourceSectionToSectionEntityMapper(sectionTypeMapper)
    }

    @Test
    fun `map should convert ResourceSection to SectionEntity`() {
        val resourceSection = ResourceSection(
            categoryName = "Example Category",
            categoryType = SectionType.POPULAR_MOVIES,
            resources = listOf()
        )
        val expected = SectionEntity(
            categoryName = "Example Category",
            categoryType = SectionEntityType.POPULAR_MOVIES
        )

        val result = mapper.map(resourceSection)

        assertEquals(expected, result)
    }
}