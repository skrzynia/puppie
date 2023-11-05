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
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK) {
                (bind.viewPager.adapter)?.
                notifyItemRangeChanged(0, 3)
            }
        }

    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
        val launcherIntent = Intent(this, AddPlacemark::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)

    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        bind = ActivityListAllPlacemarksBinding.inflate(layoutInflater)
//        setContentView(bind.root)
//        bind.addToolbar.title = title
//        setSupportActionBar(bind.addToolbar)
//
//        app = application as MainApp
//
//        val list = listOf(PlacemarkModel("Uno","Due"), PlacemarkModel("Duo", "Tre"), PlacemarkModel("Duo", "Tre"))
//        bind.viewPager.adapter = PlacemarkAdapter(list, this)
//        TabLayoutMediator(bind.tabLayout, bind.viewPager) {tab, position ->
//            val tabNames = listOf("All", "Popular", "Recomended")
//            tab.text = tabNames[position]
//        }.attach()
//
//    }
//
//    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
//        val launcherIntent = Intent(this, AddPlacemark::class.java)
//        launcherIntent.putExtra("placemark_edit", placemark)
//        this.position = position
//        getClickResult.launch(launcherIntent)
//
//    }
//
//    private val getClickResult =
//        registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                (bind.viewPager.adapter)?.
//                notifyItemRangeChanged(0,list.size)
//            }
//            else // Deleting
//                if (it.resultCode == 99)
//                    (bind.viewPager.adapter)?.notifyItemRemoved(position)
//        }
}
