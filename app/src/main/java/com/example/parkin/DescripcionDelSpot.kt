package com.example.parkin

import android.app.AlertDialog
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class DescripcionDelSpot : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_del_spot)
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
        val btnPerfilPropietario = findViewById<ImageView>(R.id.btn_Profile)
        btnPerfilPropietario.setOnClickListener {
            val intent = Intent(this, MiPerfilPropietario::class.java)
            startActivity(intent)
        }

        // Acciones
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString("id").toString() // Obtiene el valor del extra utilizando la clave

        }

    }

    fun mostrarAlerta(valor: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Bienvenido")
        alertDialog.setMessage(valor)
        alertDialog.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
}