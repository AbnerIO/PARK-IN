package com.example.parkin

import android.app.AlertDialog
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

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

        val URL = "http://192.168.0.7:5000"

        // Acciones
        val extras = intent.extras
        if (extras != null) {
            val spot_id = extras.getString("id").toString() // Obtiene el valor del extra utilizando la clave
            val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
            // peticion
            val client = OkHttpClient()
            // Enlace y método
            val request = Request.Builder()
                .url("$URL/spot/$spot_id")
                .get()
                .build()
            // Corrutinado (ejecución)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = client.newCall(request).execute()
                    val responseData = response.body?.string()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val json = JSONObject(responseData)

                            // Acceder a los valores de las propiedades
                            val street = json.getString("street")
                            val number = json.getString("street_number")
                            val neighborhood = json.getString("neighborhood")
                            val city = json.getString("city")
                            val car_spaces = json.getString("car_spaces")
                            val car_price = json.getString("car_space_rent")
                            val bike_spaces = json.getString("bicycle_spaces")
                            val bike_price = json.getString("bicycle_space_rent")

                            val textEspaciosAutos = findViewById<TextView>(R.id.textViewEspaciosAutos)
                            val textEspaciosBicis = findViewById<TextView>(R.id.textViewEspaciosBicis)
                            val textPrecioBicis = findViewById<TextView>(R.id.textViewPreciosBicis)
                            val textPrecioAutos = findViewById<TextView>(R.id.textViewPreciosAutos)
                            val textDireccion = findViewById<TextView>(R.id.textViewDireccion)

                            textEspaciosAutos.text = car_spaces
                            textPrecioAutos.text = car_price
                            textEspaciosBicis.text = bike_spaces
                            textPrecioBicis.text = bike_price
                            textDireccion.text = "$number $street $neighborhood"
                        } else {
                            val json = JSONObject(responseData)
                            // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                            mostrarAlerta(json.toString())

                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        mostrarAlerta("Error en la solicitud: ${e.message}")
                    }
                }
            }
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