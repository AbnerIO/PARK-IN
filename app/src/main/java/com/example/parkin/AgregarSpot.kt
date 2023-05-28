package com.example.parkin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class AgregarSpot : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_spot)
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


        val btnConfirmar = findViewById<Button>(R.id.btn_Confirmar)
        btnConfirmar.setOnClickListener {

            val intentMisSpots = Intent(this, MisSpots::class.java)

            val calle = findViewById<EditText>(R.id.input_calle).text.toString()
            val numero = findViewById<EditText>(R.id.input_numero).text.toString()
            val colonia = findViewById<EditText>(R.id.input_colonia).text.toString()
            val ciudad = findViewById<EditText>(R.id.input_ciudad).text.toString()
            val estado = findViewById<EditText>(R.id.input_estado).text.toString()
            val espacios_autos = findViewById<EditText>(R.id.input_espacios_auto).text.toString()
            val espacios_motos = findViewById<EditText>(R.id.input_espacio_moto).text.toString()
            val precios_moto = findViewById<EditText>(R.id.input_precio_moto).text.toString()
            val precios_auto = findViewById<EditText>(R.id.input_precio_auto).text.toString()
            if (espacios_autos.isEmpty() or precios_moto.isEmpty() or precios_auto.isEmpty() or espacios_motos.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, todos los campos de precio y espacios.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val client = OkHttpClient()
                // Parámetros
                val formBody = FormBody.Builder()
                    .add("calle", calle)
                    .add("numero", numero)
                    .add("colonia", colonia)
                    .add("ciudad", ciudad)
                    .add("estado", estado)
                    .add("espacios_coches", espacios_autos)
                    .add("espacios_bicicletas", espacios_motos)
                    .add("precio_coche", precios_auto)
                    .add("precio_bicicleta", precios_moto)
                    .build()

                // Enlace y método
                val request = Request.Builder()
                    .url("http://192.168.0.8:5000/spots/add")
                    .post(formBody)
                    .build()
                // Corrutinado (ejecución)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = client.newCall(request).execute()
                        val responseData = response.body?.string()
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val json = responseData
                                // La solicitud fue exitosa (código de r espuesta en el rango 200-299)
                                if (json != null) {
                                    intentMisSpots.putExtra("message", "Spot añadido")
                                    startActivity(intentMisSpots)
                                }
                            } else {
                                val json = responseData?.let { it1 -> JSONObject(it1) }
                                val errorMessage = json?.optString("error", "Error desconocido")
                                // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                                if (errorMessage != null) {
                                    mostrarAlerta(errorMessage)
                                }
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
    }

    fun mostrarAlerta(valor: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Mensaje")
        alertDialog.setMessage(valor)
        alertDialog.setPositiveButton("Aceptar", { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }
}