package org.wit.puppie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.pappie.R
import org.wit.pappie.databinding.ActivityAddPlacemarkBinding
import org.wit.placemark.main.MainApp

class AddPlacemark : AppCompatActivity() {
    private lateinit var bind: ActivityAddPlacemarkBinding
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddPlacemarkBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.addToolbar.title = title
        setSupportActionBar(bind.addToolbar)

        app = application as MainApp
    }
}