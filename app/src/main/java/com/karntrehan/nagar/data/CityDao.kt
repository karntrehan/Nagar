package com.karntrehan.nagar.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.karntrehan.nagar.data.entities.CityEntity

/**
 * Created by karn on 13-08-2017.
 */
@Dao
interface CityDao {

    @Query("Select * from " + CityEntity.TABLE_NAME + " LIMIT :limit OFFSET :offset")
    fun loadLocalCities(offset: Int, limit: Int): LiveData<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(cities: List<CityEntity>?)

    @Query("Select count(*) from " + CityEntity.TABLE_NAME)
    fun loadCitiesCount(): Long
}