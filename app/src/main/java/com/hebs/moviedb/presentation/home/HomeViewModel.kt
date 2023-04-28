package com.hebs.moviedb.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hebs.moviedb.domain.cases.HomeUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private var sectionsResults: Set<ResourceSection> = setOf()

    private val disposable = CompositeDisposable()

    private val _sectionsLiveData: MutableLiveData<HomeSectionActions> = MutableLiveData()
    val sectionsLiveData: LiveData<HomeSectionActions> = _sectionsLiveData

    fun loadMovies() {
        disposable.add(
            homeUseCase.getData()
                .doOnTerminate {
                    _sectionsLiveData.postValue(HomeSectionActions.HideLoading)
                }
                .applySchedulers()
                .subscribe({
                    sectionsResults = sectionsResults + it
                    Log.e("hebshebs", " Nuevo resultado " + sectionsResults.size)
                    _sectionsLiveData.postValue(
                        HomeSectionActions.UpdateSections(
                            sectionsResults
                        )
                    )
                },
                    {
                        _sectionsLiveData.postValue(it.message?.let { it1 ->
                            HomeSectionActions.Error(
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