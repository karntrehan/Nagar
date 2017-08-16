package com.karntrehan.nagar.di

import com.karntrehan.nagar.CitiesViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by karn on 13-08-2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class,
        NetworkModule::class))
interface AppComponent {
    fun inject(into: CitiesViewModel)
}
