package com.karntrehan.nagar.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.karntrehan.nagar.data.NagarDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by karn on 13-08-2017.
 */
@Module(includes = arrayOf(AppModule::class))
class DataModule {

    @Singleton
    @Provides
    fun preference(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun appDatabase(context: Context): NagarDb = Room.databaseBuilder(context,
            NagarDb::class.java, NagarDb.DB_NAME).build()

    @Provides
    @Singleton
    fun cityDao(nagarDb: NagarDb) = nagarDb.cityDao()
}