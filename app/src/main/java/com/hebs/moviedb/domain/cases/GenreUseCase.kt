package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import com.hebs.moviedb.tools.applySchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    fun getGenreList(): Single<List<Genre>> {
        val genreListMovieSingle = moviesRepository.getGenreList()
        val genreListTvShowSingle = tvShowRepository.getGenreList()

        return Single.zip(
            genreListMovieSingle,
            genreListTvShowSingle
        ) { movieGenres: List<Genre>, tvShowsGenres: List<Genre> ->
            val genres = movieGenres + tvShowsGenres
            genres.distinct().sortedBy { it.name }
        }
            .applyIoSchedulers()
            .flatMap {
                moviesRepository.storeGenreList(it)
                Single.just(it)
            }
    }

    fun getByGenre(genre: Genre): Flowable<ResourceSection> {
        val byGenreMovieSingle = moviesRepository.getByGenre(genre)
        val byGenreTvShowSingle = tvShowRepository.getByGenre(genre)

        return Single.merge(
            byGenreMovieSingle,
            byGenreTvShowSingle,
        ).applySchedulers()
    }
}