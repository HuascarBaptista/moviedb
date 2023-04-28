package com.hebs.moviedb.presentation.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hebs.moviedb.domain.cases.GenreUseCase
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class GenreViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase
) : ViewModel() {

    private var byGenresResults: Set<ResourceSection> = setOf()

    private val disposable = CompositeDisposable()

    private val _genresLiveData: MutableLiveData<GenreSectionActions> = MutableLiveData()
    val genresLiveData: LiveData<GenreSectionActions> = _genresLiveData

    fun getGenres() {
        disposable.add(
            genreUseCase.getGenreList()
                .doOnTerminate {
                    _genresLiveData.postValue(GenreSectionActions.HideLoading)
                }
                .applySchedulers()
                .subscribe({
                    _genresLiveData.postValue(
                        GenreSectionActions.UpdateGenres(it)
                    )
                },
                    {
                        _genresLiveData.postValue(it.message?.let { it1 ->
                            GenreSectionActions.Error(
                                it1
                            )
                        })
                    }
                )
        )
    }

    fun getByGenre(genre: Genre) {
        disposable.add(
            genreUseCase.getByGenre(genre)
                .doOnTerminate {
                    _genresLiveData.postValue(GenreSectionActions.HideLoading)
                }
                .applySchedulers()
                .subscribe({
                    byGenresResults = byGenresResults + it
                    _genresLiveData.postValue(
                        GenreSectionActions.UpdateSections(byGenresResults)
                    )
                },
                    {
                        _genresLiveData.postValue(it.message?.let { it1 ->
                            GenreSectionActions.Error(
                                it1
                            )
                        })
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
    }
}