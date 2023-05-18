package com.example.parkin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.ComponentActivity


class FormaSolicitud : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_solicitud)

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

        val opcionesCarros = listOf(1, 2, 3, 4, 5)
        val opcionesBicicletas = listOf(1, 2)
        // spinnercarros
        val spinnerCarros = findViewById<Spinner>(R.id.spinnercar)
        val adapterCarros = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesCarros)
        adapterCarros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarros.adapter = adapterCarros
        spinnerCarros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = opcionesCarros[position]
                // Realizar acciones basadas en la opción seleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se selecciona ningún elemento
            }
        }
        //spinner bicicletas
        val spinnerBicicletas = findViewById<Spinner>(R.id.spinnerbike)
        val adapterBicicletas =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesBicicletas)
        adapterBicicletas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBicicletas.adapter = adapterBicicletas
        spinnerBicicletas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = opcionesBicicletas[position]
                // Realizar acciones basadas en la opción seleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se selecciona ningún elemento
            }
        }

        val btnPagar = findViewById<Button>(R.id.btn_pagar)
        btnPagar.setOnClickListener {
            val intent = Intent(this, PagoSolicitud::class.java)
            startActivity(intent)
        }
    }
}