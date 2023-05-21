package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MisSpots : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_spots)

        // Funciones base
        val btnTicketsPropietarios= findViewById<ImageView>(R.id.btn_ticketsPropietario)
        btnTicketsPropietarios.setOnClickListener {
            val intent = Intent(this, TicketsPropietarios::class.java)
            startActivity(intent)
        }
        val btnPerfilPropietario= findViewById<ImageView>(R.id.btn_Profile)
        btnPerfilPropietario.setOnClickListener {
            val intent = Intent(this, MiPerfilPropietario::class.java)
            startActivity(intent)
        }

        val btnEditarSpot= findViewById<Button>(R.id.btn_editarSpot)
        btnEditarSpot.setOnClickListener {
            val intent = Intent(this, DescripcionDelSpot::class.java)
            startActivity(intent)
        }


    }
}