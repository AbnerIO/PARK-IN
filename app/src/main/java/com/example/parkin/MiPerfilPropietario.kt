package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MiPerfilPropietario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil_propietario)
        // Funciones base
        val btnTicketsPropietarios = findViewById<ImageView>(R.id.btn_ticketsPropietario)
        btnTicketsPropietarios.setOnClickListener {
            val intent = Intent(this, TicketsPropietarios::class.java)
            startActivity(intent)
        }
        val btnMisSpots = findViewById<ImageView>(R.id.btn_MisSpots)
        btnMisSpots.setOnClickListener {
            val intent = Intent(this, MisSpots::class.java)
            startActivity(intent)
        }

        val btnAgregarSpot= findViewById<Button>(R.id.btn_AgregarSpot)
        btnAgregarSpot.setOnClickListener {
            val intent = Intent(this, AgregarSpot::class.java)
            startActivity(intent)
        }
    }
}