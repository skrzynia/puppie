package org.wit.puppie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.pappie.R
import org.wit.pappie.databinding.ActivityListAllPlacemarksBinding
import org.wit.placemark.main.MainApp

class ListAllPlacemarks : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var bind: ActivityListAllPlacemarksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityListAllPlacemarksBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.addToolbar.title = title

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        bind.viewPager.adapter
    }
}
