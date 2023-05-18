package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button

class MisSpots : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_spots)

        val btnEditarSpot= findViewById<Button>(R.id.btn_editarSpot)
        btnEditarSpot.setOnClickListener {
            val intent = Intent(this, DescripcionDelSpot::class.java)
            startActivity(intent)
        }
    }
}