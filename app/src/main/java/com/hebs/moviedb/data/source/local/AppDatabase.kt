package com.hebs.moviedb.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionResourceCrossEntity
import com.hebs.moviedb.data.model.local.VideoMediaEntity
import com.hebs.moviedb.data.source.local.source.ResourceDataSource

@Database(
    entities = [ResourceEntity::class, VideoMediaEntity::class, SectionEntity::class, SectionResourceCrossEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDataSource(): ResourceDataSource
}