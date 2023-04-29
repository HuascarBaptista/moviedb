package com.hebs.moviedb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hebs.moviedb.domain.cases.HomeUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.presentation.base.BaseViewModel
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : BaseViewModel() {

    private var sectionsResults: Set<ResourceSection> = setOf()

    private val _sectionsLiveData: MutableLiveData<HomeSectionActions> = MutableLiveData()
    val sectionsLiveData: LiveData<HomeSectionActions> = _sectionsLiveData

    fun loadMovies() {
        showLoading()
        disposable.add(
            homeUseCase.getData()
                .applySchedulers()
                .subscribe(
                    {
                        hideLoading()
                        sectionsResults = sectionsResults + it
                        _sectionsLiveData.postValue(
                            HomeSectionActions.UpdateSections(
                                sectionsResults
                            )
                        )
                    },
                    {
                        hideLoading()
                        sendError(it.message)
                    }
                )
        )
    }
}