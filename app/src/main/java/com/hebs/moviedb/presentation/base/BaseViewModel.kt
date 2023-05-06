package com.hebs.moviedb.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    private val _baseFragmentActionLiveData: MutableLiveData<BaseViewActions> = MutableLiveData()
    val baseFragmentActionLiveData: LiveData<BaseViewActions> = _baseFragmentActionLiveData

    protected val disposable = CompositeDisposable()

    protected fun hideLoading() {
        _baseFragmentActionLiveData.postValue(BaseViewActions.HideLoading)
    }

    protected fun showLoading() {
        _baseFragmentActionLiveData.postValue(BaseViewActions.ShowLoading)
    }

    override fun onCleared() {
        disposable.clear()
    }

    fun sendError() {
        _baseFragmentActionLiveData.postValue(BaseViewActions.Error)
    }
}