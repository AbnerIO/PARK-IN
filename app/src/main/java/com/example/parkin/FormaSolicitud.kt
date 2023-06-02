package com.example.parkin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
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


        val extras = intent.extras
        if (extras != null) {
            val map_url = extras.getString("map_url")
            val id = extras.getString("id")
            val bike_price = extras.getString("bike_price")
            val car_price = extras.getString("car_price")
            val car_spaces = extras.getString("car_spaces")
            val bike_spaces = extras.getString("bike_spaces")


            if (map_url != null && id != null && bike_price != null && car_price != null && bike_spaces != null && car_spaces != null) {
                var bicis = ""
                var carros = ""
                var entrada = ""
                var salida = ""
                val opcionesCarros = mutableListOf<Int>()
                for (i in 1..car_spaces.toInt()) {
                    opcionesCarros.add(i)
                }
                val opcionesBicicletas = mutableListOf<Int>()
                for (i in 1..bike_spaces.toInt()) {
                    opcionesBicicletas.add(i)
                }
                val btn_verMapa = findViewById<Button>(R.id.btn_verUrl)
                btn_verMapa.setOnClickListener{
                    val intentURL = Intent(Intent.ACTION_VIEW)
                    intentURL.data = Uri.parse(map_url)
                    startActivity(intentURL)
                }
                val car_price_view = findViewById<TextView>(R.id.CAR_PRICE)
                car_price_view.text = "$$car_price"
                val bike_price_view = findViewById<TextView>(R.id.BIKEPRICE)
                bike_price_view.text = "$$bike_price"

                // spinnercarros
                val spinnerCarros = findViewById<Spinner>(R.id.spinnercar)
                val adapterCarros =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesCarros)
                adapterCarros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCarros.adapter = adapterCarros
                spinnerCarros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        carros = opcionesCarros[position].toString()
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
                spinnerBicicletas.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            bicis = opcionesBicicletas[position].toString()
                            // Realizar acciones basadas en la opción seleccionada
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Acciones cuando no se selecciona ningún elemento
                        }
                    }

                val btnPagar = findViewById<Button>(R.id.btn_pagar)
                btnPagar.setOnClickListener {
                    val entradaInput = findViewById<EditText>(R.id.editTextTime)
                    val salidaInput = findViewById<EditText>(R.id.editTextTime1)
                    entrada = entradaInput.text.toString()
                    entrada = salidaInput.text.toString()
                    val intentPagoSolicitud = Intent(this, PagoSolicitud::class.java)
                    val extras = Bundle()
                    val nota = findViewById<TextView>(R.id.Nota).text.toString()

                    extras.putString("id", id)
                    extras.putString("entrada", entrada)
                    extras.putString("salida", salida)
                    extras.putString("bike_spaces", bicis)
                    extras.putString("car_spaces", carros)
                    extras.putString("nota", nota)

                    intentPagoSolicitud.putExtras(extras)
                    startActivity(intentPagoSolicitud)
                }
            }
        }

    }
}