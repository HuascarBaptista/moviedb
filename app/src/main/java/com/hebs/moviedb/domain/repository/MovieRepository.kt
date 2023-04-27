package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.ResourceSection
import io.reactivex.rxjava3.core.Single

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface MovieRepository {

    fun getPopularMoviesSection(): Single<ResourceSection>

    fun getTopRatedMoviesSection(): Single<ResourceSection>

    fun getMovieVideos(id: Int)

    fun search(term: String): Single<ResourceSection>
}