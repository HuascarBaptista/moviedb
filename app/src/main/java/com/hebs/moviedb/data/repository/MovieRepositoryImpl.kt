package com.hebs.moviedb.data.repository

import com.hebs.moviedb.data.mappers.SectionEntityToResourceSectionMapper
import com.hebs.moviedb.data.source.remote.MovieRemoteDataSource
import com.hebs.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: SectionEntityToResourceSectionMapper
) : MovieRepository {

    override fun getPopularMoviesSection() =
        movieRemoteDataSource.getPopularMovies()
            .map {
                mapper.fromEntity(it)
            }

    override fun getTopRatedMoviesSection() =
        movieRemoteDataSource.getTopRatedMovies()
            .map {
                mapper.fromEntity(it)
            }

    override fun getMovieVideos(id: Int) = movieRemoteDataSource.getMovieVideos(id)

    override fun search(term: String) =
        movieRemoteDataSource.search(term)
            .map {
                mapper.fromEntity(it)
            }

}