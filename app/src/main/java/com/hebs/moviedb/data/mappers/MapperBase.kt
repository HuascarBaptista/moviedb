package com.hebs.moviedb.data.mappers

interface MapperBase<T, P> {
    fun fromEntity(entity: T): P
}