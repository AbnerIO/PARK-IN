package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class PagoSolicitud : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_solicitud)
        //Botones principales
        val btnADondeVamos = findViewById<ImageView>(R.id.btn_ADondeVamos)
        btnADondeVamos.setOnClickListener {
            val intent = Intent(this, ADondeVamos::class.java)
            startActivity(intent)
        }
        val btnProfile = findViewById<ImageView>(R.id.btn_Profile)
        btnProfile.setOnClickListener {
            val intent = Intent(this, MiPerfilCliente::class.java)
            startActivity(intent)
        }

        val btnTicketsUsuario = findViewById<ImageView>(R.id.btn_ticketsUsuario)
        btnTicketsUsuario.setOnClickListener {
            val intent = Intent(this, TicketsCliente::class.java)
            startActivity(intent)
        }


        val btnPagoSolicitud = findViewById<Button>(R.id.btn_pagoSolicitud)
        btnPagoSolicitud.setOnClickListener {
            val intent = Intent(this, ADondeVamos::class.java)
            startActivity(intent)
        }



    }

}