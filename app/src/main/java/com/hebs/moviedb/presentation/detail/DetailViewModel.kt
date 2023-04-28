package com.hebs.moviedb.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hebs.moviedb.domain.cases.DetailUseCase
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.actions.DetailViewActions
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _videoMediaLiveData: MutableLiveData<DetailViewActions> = MutableLiveData()
    val videoMediaLiveData: LiveData<DetailViewActions> = _videoMediaLiveData

    fun loadMovies(resource: Resource) {
        disposable.add(
            detailUseCase.getVideosMedia(resource)
                .applySchedulers()
                .subscribe({
                    _videoMediaLiveData.postValue(
                        DetailViewActions.UpdateVideos(it)
                    )
                },
                    {
                        _videoMediaLiveData.postValue(it.message?.let { it1 ->
                            DetailViewActions.Error(
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