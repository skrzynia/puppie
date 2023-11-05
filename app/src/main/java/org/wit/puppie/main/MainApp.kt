package org.wit.placemark.main

import android.app.Application
import androidx.viewpager2.widget.ViewPager2
import org.wit.puppie.models.PlacemarkJSONStore
import org.wit.puppie.models.PlacemarkStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {
    lateinit var placemarks:PlacemarkStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        placemarks = PlacemarkJSONStore(applicationContext)
        i("Puppie started")
    }
}