package com.karntrehan.nagar

import android.app.Application
import com.facebook.stetho.Stetho
import com.karntrehan.nagar.di.AppComponent
import com.karntrehan.nagar.di.AppModule
import com.karntrehan.nagar.di.DaggerAppComponent

/**
 * Created by karn on 13-08-2017.
 */
class NagarApplication : Application() {


    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}