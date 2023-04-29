package com.hebs.moviedb.presentation.base

sealed class BaseViewActions {
    object ShowLoading : BaseViewActions()
    object HideLoading : BaseViewActions()
    object Error : BaseViewActions()
}
