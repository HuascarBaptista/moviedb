package com.hebs.moviedb.data.source.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hebs.moviedb.data.model.local.GenreEntity
import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionResourceCrossEntity
import com.hebs.moviedb.data.model.local.SectionWithResources
import com.hebs.moviedb.data.model.local.VideoMediaEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface ResourceDataSource {
    @Transaction
    @Query(
        """
        SELECT section_resource.* 
        FROM section_resource
        WHERE section_resource.categoryName = :categoryName
        """
    )
    fun getSectionWithResourcesByCategoryName(categoryName: String): Single<SectionWithResources>

    @Transaction
    @Query("SELECT * FROM video WHERE resourceId = :resourceId")
    fun getVideosMediaByResourceId(resourceId: Int): Single<List<VideoMediaEntity>>

    @Transaction
    @Query("SELECT * FROM genre ORDER BY name asc")
    fun getGenreList(): Single<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResources(resource: List<ResourceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSection(sectionEntity: SectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionWithResource(sectionWithResources: SectionResourceCrossEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoMediaEntity(videoMediaEntity: List<VideoMediaEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(resources: List<ResourceEntity>, sectionEntity: SectionEntity) {
        insertSection(sectionEntity)
        insertResources(resources)
        resources.forEach {
            insertSectionWithResource(SectionResourceCrossEntity(sectionEntity.categoryName, it.id))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreList(genreEntity: List<GenreEntity>)
}