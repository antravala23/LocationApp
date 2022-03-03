package com.example.locationapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : AppCompatActivity() {

    var list: ArrayList<LocationModel> = ArrayList()
    var adapter: LocationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        list.add("Surat")
//        list.add("Ahmedabad")
//        list.add("Mumbai")

        adapter = LocationListAdapter(this, list)

        list_rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_rvList.adapter = adapter

        list_btnAdd.setOnClickListener {
            val intent = Intent(this, AddLocationActivity::class.java)
            startActivityForResult(intent, 111)
        }

        list_btnSort.setOnClickListener { showDialog(this@MainActivity) }

        list_btnPath.setOnClickListener {

            if (list.size > 0) {
                val gson = Gson()
                val jsonString = gson.toJson(list)

                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("ll", jsonString)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Please select location", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // success
                data.apply {
                    val name = getStringExtra("RESULT_NAME")
                    val lat = getDoubleExtra("RESULT_LAT", 0.0)
                    val lng = getDoubleExtra("RESULT_LNG", 0.0)

                    if(list.size > 0) {
                        list.add(LocationModel(name!!, false, LatLng(lat, lng)))
                    }else{
                        list.add(LocationModel(name!!, true, LatLng(lat, lng)))
                    }

                    adapter!!.notifyDataSetChanged()
                    list_rvList.adapter = adapter
                    // do something
                }
            } else {
                // fail
            }
        }
    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_sorting)

        val aes = dialog.findViewById(R.id.list_tvAes) as TextView
        val des = dialog.findViewById(R.id.list_tvDes) as TextView

        aes.setOnClickListener {

//            list.sortedWith(SortPlaces(list[0].lat))
            Collections.sort(list, SortPlaces(list[0].lat));

            Log.e("", "" + list)

            Toast.makeText(this@MainActivity, "" + list, Toast.LENGTH_SHORT).show()

            adapter!!.notifyDataSetChanged()

            dialog.dismiss()

            distance(list)

        }

        des.setOnClickListener { dialog.dismiss() }

        dialog.show()

    }

    fun distance(list: ArrayList<LocationModel>) {

        var temparr: HashMap<Double, ArrayList<LocationModel>> = HashMap()

        for (i in 1 until list.size) {

            if (i < list.size - 1) {
                val theta = list[0].lat.longitude - list[i].lat.longitude
                var dist = (sin(deg2rad(list[0].lat.latitude))
                        * sin(deg2rad(list[i].lat.latitude))
                        + (cos(deg2rad(list[0].lat.latitude))
                        * cos(deg2rad(list[i].lat.latitude))
                        * cos(deg2rad(theta))))
                dist = acos(dist)
                dist = rad2deg(dist)
                dist *= 60 * 1.1515

                Log.e("", "" + list[i].locatinname + ":" + (dist / 0.62137))
                Toast.makeText(this@MainActivity, "" + dist, Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}