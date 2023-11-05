package org.wit.puppie.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.pappie.R
import org.wit.pappie.databinding.CardDetailsActivityBinding
import org.wit.puppie.models.Location
import org.wit.puppie.models.PlacemarkModel

class PlacemarkDetails : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: CardDetailsActivityBinding
    private var location = Location()
    var placemark = PlacemarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        location = AddPlacemark().location!!

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        if (intent.hasExtra("placemark_details")) {
            placemark = intent.extras?.getParcelable("placemark_details")!!
            binding.description.setText(placemark.description)
            binding.title.setText(placemark.title)
            binding.image.setImageURI(placemark.image)
            Picasso.get()
                .load(placemark.image)
                .into(binding.image)
        }
        if (intent.hasExtra("location")){
            location = intent.extras?.getParcelable("location")!!
            placemark.lat = location.lat
            placemark.lng = location.lng
            placemark.zoom = location.zoom
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Log.i("asd","${location.lat}")
        val loc = LatLng(placemark.lat, placemark.lng)
        val options = MarkerOptions()
            .title("Placemark")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.setOnMarkerDragListener(this)
        map.setOnMarkerClickListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        Log.i("position latitude", "${marker.position.latitude}")
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragStart(p0: Marker) {}

    override fun onMarkerClick(marker: Marker): Boolean {
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
        return false
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}