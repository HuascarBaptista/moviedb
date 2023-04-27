package com.hebs.moviedb.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionResourceCrossEntity
import com.hebs.moviedb.data.model.local.VideoEntity
import com.hebs.moviedb.data.source.local.source.LocalDataSource

@Database(
    entities = [ResourceEntity::class, VideoEntity::class, SectionEntity::class, SectionResourceCrossEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDataSource(): LocalDataSource
}