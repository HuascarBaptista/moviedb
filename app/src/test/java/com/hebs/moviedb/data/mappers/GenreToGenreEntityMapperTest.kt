package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.GenreEntity
import com.hebs.moviedb.domain.model.Genre
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test


class GenreToGenreEntityMapperTest {

    private lateinit var mapper: GenreToGenreEntityMapper

    @Before
    fun setUp() {
        mapper = GenreToGenreEntityMapper()
    }

    @Test
    fun `map should convert Genre to GenreEntity`() {
        val genre = Genre(1, "Action")
        val expected = GenreEntity(1, "Action")

        val result = mapper.map(genre)

        assertEquals(expected, result)
    }
}
