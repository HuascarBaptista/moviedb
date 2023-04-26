package com.hebs.moviedb.data.repository

import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    /*
    DO On Error
    movieRemoteDataSource.getPopularMovies().doOnError {
            fetchCachedPopularMovies()
        }
     */
    override fun fetchPopularMovies() =
        movieRemoteDataSource.getPopularMovies()
}