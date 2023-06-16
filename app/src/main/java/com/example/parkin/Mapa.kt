package com.example.parkin

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Mapa : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        address = intent.getStringExtra("address").toString()
        println("Adreesasdasdasdasd : " + address)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val geocoder = Geocoder(this)
        val locationList = geocoder.getFromLocationName(address.toString(),1)
        if (locationList!!.isNotEmpty()) {
            val location = locationList!![0]
            val coordinates = LatLng(location.latitude, location.longitude)
            map.addMarker(
                MarkerOptions()
                    .position(coordinates)
                    .title(address)
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
        }


    }

    private fun createMarker() {
        val coordenadas = LatLng(100.20, 200.1)

        map.addMarker(
            MarkerOptions()
                .position(coordenadas)
                .title("Mi Marcador")
        )

        map.animateCamera(
            CameraUpdateFactory.newLatLng(coordenadas),
            4000, null
        )
    }
}