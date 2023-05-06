package com.hebs.moviedb.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hebs.moviedb.domain.cases.DetailUseCase
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.actions.DetailViewActions
import com.hebs.moviedb.presentation.base.BaseViewModel
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase
) : BaseViewModel() {

    private val _videoMediaLiveData: MutableLiveData<DetailViewActions> = MutableLiveData()
    val videoMediaLiveData: LiveData<DetailViewActions> = _videoMediaLiveData

    fun loadMovies(resource: Resource) {
        showLoading()
        disposable.add(
            detailUseCase.getVideosMedia(resource)
                .applySchedulers()
                .subscribe(
                    {
                        hideLoading()
                        _videoMediaLiveData.postValue(
                            DetailViewActions.UpdateVideos(it)
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