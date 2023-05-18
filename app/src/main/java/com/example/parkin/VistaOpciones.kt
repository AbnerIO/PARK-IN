package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView

class VistaOpciones : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_opciones)

        //Botones principales
        val btnADondeVamos = findViewById<ImageView>(R.id.btn_ADondeVamos)
        btnADondeVamos.setOnClickListener {
            val intent = Intent(this, ADondeVamos::class.java)
            startActivity(intent)
        }
        val btnProfile= findViewById<ImageView>(R.id.btn_Profile)
        btnProfile.setOnClickListener {
            val intent = Intent(this, MiPerfilCliente::class.java)
            startActivity(intent)
        }

        val cardContainer = findViewById<CardView>(R.id.CardView1)
        cardContainer.setOnClickListener {
            // Acción cuando se hace clic en el CardView
            val intent = Intent(this, FormaSolicitud::class.java)
            startActivity(intent)
        }
    }
}