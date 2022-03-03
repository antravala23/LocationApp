package com.example.locationapp

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class SortPlaces(val currentLoc: LatLng) : Comparator<LocationModel?> {

    override fun compare(place1: LocationModel?, place2: LocationModel?): Int {

        val lat1: Double = place1!!.lat.latitude
        val lon1: Double = place1.lat.longitude
        val lat2: Double = place2!!.lat.latitude
        val lon2: Double = place2.lat.longitude

        val distanceToPlace1 = distance1(currentLoc.latitude, currentLoc.longitude, lat1, lon1)
        val distanceToPlace2 = distance1(currentLoc.latitude, currentLoc.longitude, lat2, lon2)
        return (distanceToPlace1 - distanceToPlace2).toInt()
    }

    fun distance(fromLat: Double, fromLon: Double, toLat: Double, toLon: Double): Double {
        val radius = 6378137.0 // approximate Earth radius, *in meters*
        val deltaLat = toLat - fromLat
        val deltaLon = toLon - fromLon
        val angle = 2 * Math.asin(
            Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2.0) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                        Math.pow(Math.sin(deltaLon / 2), 2.0)
            )
        )
        return radius * angle
    }

    fun distance1(fromLat: Double, fromLon: Double, toLat: Double, toLon: Double): Double {

        var temparr: HashMap<Double, ArrayList<LocationModel>> = HashMap()

//        for (i in 0 until list.size) {

//            if (i < list.size - 1) {
                val theta = fromLon - toLon
                var dist = (sin(deg2rad(fromLat))
                        * sin(deg2rad(toLat))
                        + (cos(deg2rad(fromLat))
                        * cos(deg2rad(toLat))
                        * cos(deg2rad(theta))))
                dist = acos(dist)
                dist = rad2deg(dist)
                dist *= 60 * 1.1515

        return dist
//                Log.e("", "" + list[i].locatinname + ":" + (dist / 0.62137))
//                Toast.makeText(this@MainActivity, "" + (dist / 0.62137), Toast.LENGTH_SHORT).show()
//            }

//        }

    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }



}