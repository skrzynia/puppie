package org.wit.puppie.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import timber.log.Timber
import timber.log.Timber.Forest.i
import com.google.android.material.tabs.TabLayoutMediator
import org.wit.pappie.R
import org.wit.pappie.databinding.ActivityListAllPlacemarksBinding
import org.wit.placemark.main.MainApp
import org.wit.puppie.adapters.AllPlacemarksAdapter
import org.wit.puppie.adapters.PlacemarkAdapter
import org.wit.puppie.adapters.PlacemarkListener
import org.wit.puppie.models.PlacemarkModel

class ListAllPlacemarks : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var bind: ActivityListAllPlacemarksBinding
    private var position: Int = 0
    private lateinit var list: List<PlacemarkModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        bind = ActivityListAllPlacemarksBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        bind.addToolbar.title = title
        setSupportActionBar(bind.addToolbar)
        val viewPager = bind?.viewPager
        val adapter = AllPlacemarksAdapter(this)
        viewPager?.adapter = adapter

        app = application as MainApp

        bind.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        TabLayoutMediator(bind!!.tabLayout, viewPager!!) {tab, position ->
            val tabNames = listOf("All", "Popular", "Recomended")
            tab.text = tabNames[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AddPlacemark::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_delete ->
            {
                setResult(99)
                app.placemarks.deleteAll()
                finish()
                startActivity(Intent(this,ListAllPlacemarks::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK) {
                (bind.viewPager.adapter)?.
                notifyItemRangeChanged(0, app.placemarks.findAll().size)
            }
        }

    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
        val launcherIntent = Intent(this, AddPlacemark::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
    }

}
