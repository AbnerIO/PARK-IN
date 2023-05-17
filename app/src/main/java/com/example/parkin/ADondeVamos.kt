package com.example.parkin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button

class ADondeVamos : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adonde_vamos)

        val btnBuscar = findViewById<Button>(R.id.btn_buscar)
        btnBuscar.setOnClickListener {
            val intent = Intent(this, VistaOpciones::class.java)
            startActivity(intent)
        }
    }
}