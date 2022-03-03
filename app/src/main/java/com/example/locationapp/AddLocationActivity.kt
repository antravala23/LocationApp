package com.example.locationapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place

import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import java.util.*
import android.view.MenuItem
import androidx.core.app.ActivityCompat.startActivityForResult

import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import com.google.android.libraries.places.widget.Autocomplete

import android.content.Intent
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.gms.maps.model.LatLng

import com.google.android.libraries.places.widget.AutocompleteActivity
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocationActivity : AppCompatActivity() {

    var locationname = ""
    var lat: Double = 0.0
    var lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        val apiKey = getString(R.string.api_key)

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

// Create a new Places client instance.

// Create a new Places client instance.
        val placesClient = Places.createClient(this)

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        autocompleteFragment!!.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autocompleteFragment!!.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.name + ", " + place.latLng)

                locationname = place.name!!
                lat = place.latLng!!.latitude
                lng = place.latLng!!.longitude
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })

        addlocatin_btnAdd.setOnClickListener {

            val data = Intent().apply {
                putExtra("RESULT_NAME", locationname)
                putExtra("RESULT_LAT", lat)
                putExtra("RESULT_LNG", lng)
            }
            setResult(RESULT_OK, data)
            finish()

        }

    }

}