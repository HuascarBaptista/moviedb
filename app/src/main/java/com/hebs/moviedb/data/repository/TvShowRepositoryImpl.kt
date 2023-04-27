package com.hebs.moviedb.data.repository

import com.hebs.moviedb.data.mappers.SectionEntityToResourceSectionMapper
import com.hebs.moviedb.data.source.remote.TvShowRemoteDataSource
import com.hebs.moviedb.domain.repository.TvShowRepository
import javax.inject.Inject


class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val mapper: SectionEntityToResourceSectionMapper
) : TvShowRepository {

    override fun getPopularTvShowsSection() =
        tvShowRemoteDataSource.getPopularTvShows()
            .map {
                mapper.fromEntity(it)
            }

    override fun getTopRatedTvShowsSection() =
        tvShowRemoteDataSource.getTopRatedTvShows()
            .map {
                mapper.fromEntity(it)
            }

    override fun getTvShowVideos(id: Int) = tvShowRemoteDataSource.getTvShowVideos(id)

    override fun search(term: String) =
        tvShowRemoteDataSource.search(term)
            .map {
                mapper.fromEntity(it)
            }

}