package com.hebs.moviedb.presentation.detail

import com.hebs.moviedb.domain.model.Genre

interface GenreListener {
    fun genreSelected(genre: Genre)
}