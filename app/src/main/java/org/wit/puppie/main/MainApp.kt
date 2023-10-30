package org.wit.placemark.main

import android.app.Application
import androidx.viewpager2.widget.ViewPager2
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Puppie started")
    }
}