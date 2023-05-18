package com.example.parkin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ADondeVamos : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adonde_vamos)
        //Botones principales
        val btnProfile= findViewById<ImageView>(R.id.btn_Profile)
        btnProfile.setOnClickListener {
            val intent = Intent(this, MiPerfilCliente::class.java)
            startActivity(intent)
        }


        val btnBuscar = findViewById<Button>(R.id.btn_buscar)
        btnBuscar.setOnClickListener {
            val intent = Intent(this, VistaOpciones::class.java)
            startActivity(intent)
        }
    }
}