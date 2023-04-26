package com.hebs.moviedb.data.wrappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.Movie
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ResourceEntityMovieWrapperTest {

    @Test
    fun `fromEntity should map ResourceEntity to Movie correctly`() {
        val entity = ResourceEntity(
            id = 135,
            title = "Hebs Title",
            overview = "Hebs Overview",
            rating = 8.5,
            posterImageURL = "/posterImageUrl.jpg",
            coverImageURL = "/coverImageUrl.jpg"
        )
        val result: Movie = ResourceEntityToMovieWrapper.fromEntity(entity)
        assertEquals(entity.id, result.id)
        assertEquals(entity.title, result.title)
        assertEquals(entity.overview, result.overview)
        assertEquals(entity.rating, result.rating)
        assertEquals(entity.posterImageURL, result.posterImageURL)
        assertEquals(entity.coverImageURL, result.coverImageURL)
    }
}
