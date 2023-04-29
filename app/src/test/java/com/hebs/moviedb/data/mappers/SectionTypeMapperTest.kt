package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.domain.model.SectionType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SectionTypeMapperTest {

    private lateinit var mapper: SectionTypeMapper

    @Before
    fun setUp() {
        mapper = SectionTypeMapper()
    }

    @Test
    fun `mapToEntity should convert SectionType to SectionEntityType`() {
        val input = SectionType.POPULAR_MOVIES
        val expected = SectionEntityType.POPULAR_MOVIES

        val result = mapper.mapToEntity(input)

        assertEquals(expected, result)
    }

    @Test
    fun `mapFromEntity should convert SectionEntityType to SectionType`() {
        val input = SectionEntityType.POPULAR_MOVIES
        val expected = SectionType.POPULAR_MOVIES

        val result = mapper.mapFromEntity(input)

        assertEquals(expected, result)
    }
}