package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView

class VistaOpciones : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_opciones)

        val cardContainer = findViewById<CardView>(R.id.CardView1)
        cardContainer.setOnClickListener {
            // Acción cuando se hace clic en el CardView
            val intent = Intent(this, FormaSolicitud::class.java)
            startActivity(intent)
        }
    }
}