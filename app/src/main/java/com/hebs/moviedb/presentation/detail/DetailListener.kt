package com.hebs.moviedb.presentation.detail

import com.hebs.moviedb.domain.model.Resource

interface DetailListener {
    fun showDetail(resource: Resource)
}