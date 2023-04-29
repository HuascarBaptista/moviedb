package com.hebs.moviedb.presentation.base

sealed class BaseViewActions {
    object ShowLoading : BaseViewActions()
    object HideLoading : BaseViewActions()
    data class Error(val message: String) : BaseViewActions()
}
