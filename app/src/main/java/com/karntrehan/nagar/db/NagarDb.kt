package com.karntrehan.nagar.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.karntrehan.nagar.cities.entities.CityEntity

/**
 * Created by karn on 13-08-2017.
 */

@Database(entities = arrayOf(CityEntity::class), version = 1)
abstract class NagarDb : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        const val DB_NAME = "nagar.db"
    }
}