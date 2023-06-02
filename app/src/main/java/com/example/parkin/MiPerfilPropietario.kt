package com.example.parkin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MiPerfilPropietario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil_propietario)
        val URL = "http://192.168.0.11:5000"
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

        val btnAgregarSpot = findViewById<Button>(R.id.btn_AgregarSpot)
        btnAgregarSpot.setOnClickListener {
            val intent = Intent(this, AgregarSpot::class.java)
            startActivity(intent)
        }

        val extras = intent.extras
        if (extras != null) {
            val bundle: Bundle = intent.extras ?: return
            var owner_id: Any? = null
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
                owner_id = value
            }

            if (owner_id != null) {
                // peticion
                val client = OkHttpClient()
                // Enlace y método
                val request = Request.Builder()
                    .url("$URL/users/get/$owner_id")
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
                                val name = json.getString("name")
                                val email = json.getString("email")
                                val type = json.getString("type")
                                val phone = json.getString("phone")

                                val textNombre =
                                    findViewById<TextView>(R.id.nombre)
                                val textUbicacion =
                                    findViewById<TextView>(R.id.ubicacion)
                                val textCorreo =
                                    findViewById<TextView>(R.id.correo)
                                val textTipo =
                                    findViewById<TextView>(R.id.tipo)
                                val textSpots =
                                    findViewById<TextView>(R.id.spots)
                                val textTelefono =
                                    findViewById<TextView>(R.id.telefono)

                                textNombre.text = name
                                textUbicacion.text = "México"
                                textCorreo.text = email
                                textTipo.text = type
                                textSpots.text = "Spots:"
                                textTelefono.text = phone


                            } else {
                                val json = JSONObject(responseData)
                                // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)


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
        alertDialog.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }
}