package org.wit.puppie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.wit.pappie.R
import org.wit.pappie.databinding.ActivityAddPlacemarkBinding
import org.wit.placemark.main.MainApp

class AddPlacemark : AppCompatActivity() {
    private lateinit var bind: ActivityAddPlacemarkBinding
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddPlacemarkBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        bind.cancelToolbar.title = title
        setSupportActionBar(bind?.cancelToolbar)

        app = application as MainApp

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_placemark,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}