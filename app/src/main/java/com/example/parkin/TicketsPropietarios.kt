package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.ImageView

class TicketsPropietarios : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_propietarios)
        // Funciones base

        val btnMisSpots = findViewById<ImageView>(R.id.btn_MisSpots)
        btnMisSpots.setOnClickListener {
            val intent = Intent(this, MisSpots::class.java)
            startActivity(intent)
        }
        val btnPerfilPropietario = findViewById<ImageView>(R.id.btn_Profile)
        btnPerfilPropietario.setOnClickListener {
            val intent = Intent(this, MiPerfilPropietario::class.java)
            startActivity(intent)
        }
    }
}