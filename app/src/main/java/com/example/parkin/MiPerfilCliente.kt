package com.example.parkin

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.ImageView

class MiPerfilCliente : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil_cliente)
        //Botones principales
        val btnADondeVamos = findViewById<ImageView>(R.id.btn_ADondeVamos)
        btnADondeVamos.setOnClickListener {
            val intent = Intent(this, ADondeVamos::class.java)
            startActivity(intent)
        }

    }
}