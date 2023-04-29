package com.hebs.moviedb.presentation.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hebs.moviedb.domain.cases.GenreUseCase
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.base.BaseViewModel
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase
) : BaseViewModel() {

    private var byGenresResults: Set<ResourceSection> = setOf()
    private val _genresLiveData: MutableLiveData<GenreSectionActions> = MutableLiveData()
    val genresLiveData: LiveData<GenreSectionActions> = _genresLiveData

    fun getGenres() {
        showLoading()
        disposable.add(
            genreUseCase.getGenreList()
                .applySchedulers()
                .subscribe({
                    hideLoading()
                    _genresLiveData.postValue(
                        GenreSectionActions.UpdateGenres(it)
                    )
                },
                    {
                        hideLoading()
                        sendError()
                    }
                )
        )
    }

    fun getByGenre(genre: Genre) {
        showLoading()
        disposable.add(
            genreUseCase.getByGenre(genre)
                .applySchedulers()
                .subscribe({
                    hideLoading()
                    byGenresResults = byGenresResults + it
                    _genresLiveData.postValue(
                        GenreSectionActions.UpdateSections(byGenresResults)
                    )
                },
                    {
                        hideLoading()
                        sendError()
                    }
                )
        )
    }
}