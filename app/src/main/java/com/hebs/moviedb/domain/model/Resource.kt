package com.hebs.moviedb.domain.model

import android.os.Parcelable

interface Resource: Parcelable {
    val id: Int
    val title: String
    val overview: String?
    val rating: Double
    val score: Double
    val posterImageUrl: String?
    val coverImageUrl: String?
}