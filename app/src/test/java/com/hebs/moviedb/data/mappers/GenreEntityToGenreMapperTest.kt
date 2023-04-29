package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.GenreEntity
import com.hebs.moviedb.domain.model.Genre
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class GenreEntityToGenreMapperTest {

    private lateinit var mapper: GenreEntityToGenreMapper

    @Before
    fun setUp() {
        mapper = GenreEntityToGenreMapper()
    }

    @Test
    fun `map should convert GenreEntity to Genre`() {
        val entity = GenreEntity(1, "Action")
        val expected = Genre(1, "Action")

        val result = mapper.map(entity)
        assertEquals(expected, result)
    }
}