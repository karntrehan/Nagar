package com.karntrehan.nagar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by karn on 13-08-2017.
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

}