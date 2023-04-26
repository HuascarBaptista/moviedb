package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.Movie
import io.reactivex.rxjava3.core.Single

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface MovieRepository {

    fun fetchPopularMovies(): Single<List<Movie>>
}