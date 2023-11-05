package org.wit.puppie.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.pappie.R
import org.wit.pappie.databinding.ActivityAddPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.showImagePicker
import org.wit.puppie.models.Location
import org.wit.puppie.models.PlacemarkModel
import timber.log.Timber
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class AddPlacemark : AppCompatActivity() {
    private lateinit var bind: ActivityAddPlacemarkBinding
    var placemark = PlacemarkModel()
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    lateinit var app: MainApp
    var savedImage: String? = ""
    var location:Location? = Location(19.92,11.09)
    var edit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddPlacemarkBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        bind.cancelToolbar.title = title
        setSupportActionBar(bind?.cancelToolbar)

        app = application as MainApp

        bind.btnAdd.setOnClickListener() {
            placemark.title = bind.placemarkTitle.text.toString()
            placemark.description = bind.description.text.toString()
            if (savedImage != "")
                placemark.image = Uri.parse(savedImage)
            if (placemark.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.placemarks.update(placemark.copy())
                } else {
                    app.placemarks.create(placemark.copy())
                }
            }
            Timber.i("add Button Pressed: $placemark")

            setResult(RESULT_OK)
            finish()
        }

        bind.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher,this)
        }

        bind.placemarkLocation.setOnClickListener {
            location = Location(52.245696, -7.139102, 15f)
            if (placemark.zoom != 0f) {
                location!!.lat =  placemark.lat
                location!!.lng = placemark.lng
                location!!.zoom = placemark.zoom
            }
            val launcherIntent = Intent(this, PlacemarkDetails::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        bind.btnCamera.setOnClickListener {
            var cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 22)
        }

        registerImagePickerCallback()
        registerMapCallback()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 22 && resultCode == RESULT_OK){
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            var photo: Bitmap? = data?.extras?.get("data") as Bitmap
            bind.placemarkImage.setImageBitmap(photo)
            savedImage = MediaStore.Images.Media.insertImage(
                contentResolver, photo, "IMG${sdf.format(Date())}","Photo"
            )?: ""

        }
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            placemark.image = image

                            Picasso.get()
                                .load(placemark.image)
                                .into(bind.placemarkImage)
                            bind.chooseImage.setText(R.string.change_placemark_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            placemark.lat = location.lat
                            placemark.lng = location.lng
                            placemark.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}