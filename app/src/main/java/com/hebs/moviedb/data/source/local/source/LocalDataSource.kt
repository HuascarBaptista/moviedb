package com.hebs.moviedb.data.source.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hebs.moviedb.data.model.local.ResourceEntity
import com.hebs.moviedb.data.model.local.SectionEntity
import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.data.model.local.SectionResourceCrossEntity
import com.hebs.moviedb.data.model.local.SectionWithResources
import io.reactivex.rxjava3.core.Single

@Dao
interface LocalDataSource {
    @Transaction
    @Query("SELECT * FROM section WHERE categoryType = :categoryType")
    fun getSectionBySectionType(categoryType: SectionEntityType): Single<SectionWithResources>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResources(resource: List<ResourceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSection(sectionEntity: SectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionWithResource(sectionWithResources: SectionResourceCrossEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(resources: List<ResourceEntity>, sectionEntity: SectionEntity) {
        insertSection(sectionEntity)
        insertResources(resources)
        resources.forEach {
            insertSectionWithResource(SectionResourceCrossEntity(sectionEntity.categoryName, it.id))
        }
    }

}