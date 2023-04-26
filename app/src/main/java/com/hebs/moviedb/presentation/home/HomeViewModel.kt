package com.hebs.moviedb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hebs.moviedb.domain.cases.DiscoverMoviesUseCase
import com.hebs.moviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _moviesLiveData: MutableLiveData<List<Movie>?> = MutableLiveData()
    val moviesLiveData: LiveData<List<Movie>?> = _moviesLiveData

    fun loadMovies() {
        disposable.add(
            discoverMoviesUseCase.getData().subscribe(
                {
                    _moviesLiveData.postValue(it)
                },
                {
                    it.printStackTrace()
                }
            )
        )
    }
}