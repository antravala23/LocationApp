package com.example.locationapp

import com.google.android.gms.maps.model.LatLng

data class LocationModel(

    var locatinname: String,

    var isFirst: Boolean,

    var lat: LatLng,
)