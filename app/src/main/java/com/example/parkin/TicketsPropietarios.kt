package com.example.parkin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
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
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketsPropietarios : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_propietarios)
        // Funciones base
        var user_id = "1"
        val intentTickets = Intent(this, TicketsPropietarios::class.java)
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


        val extras = intent.extras
        if (extras == null) {

            // peticion
            val client = OkHttpClient()
            // Enlace y método
            val formBody = FormBody.Builder()
                .add("user_id", user_id)
                .build()

            val request = Request.Builder()
                .url("https://scientific-machine-production.up.railway.app/ver_tickets/usuario")
                .post(formBody)
                .build()
            // Corrutinado (ejecución)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = client.newCall(request).execute()
                    val responseData = response.body?.string()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val json = JSONObject(responseData)
                            // La solicitud fue exitosa (código de respuesta en el rango 200-299)

                            // Obtener el array "spots" del objeto JSON
                            val spotsArray = json.getJSONArray("tickets")

                            // Recorrer el array de spots
                            for (i in 0 until spotsArray.length()) {

                                // Obtener el objeto spot en la posición actual
                                val spotObject = spotsArray.getJSONObject(i)

                                // Acceder a los valores de las propiedades
                                val direccion = spotObject.getString("direccion")
                                val id = spotObject.getString("id")
                                val estatus = spotObject.getString("estatus")
                                var entrada = spotObject.getString("hora_entrada")
                                var salida = spotObject.getString("hora_salida")
                                val spot_id = spotObject.getString("spot_id")
                                val map_url = spotObject.getString("map_url")

                                fun convertirTimestampAFechaHora(timestampString: String): String {
                                    val timestamp = timestampString.toLong() * 1000

                                    val dateFormat =
                                        SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
                                    val date = Date(timestamp)

                                    return dateFormat.format(date)
                                }

                                val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
                                val cardView = CardView(this@TicketsPropietarios)
                                cardView.id = View.generateViewId()
                                val layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                                layoutParams.setMargins(16, 16, 16, 16)
                                cardView.layoutParams = layoutParams
                                cardView.radius = 20f
                                cardView.cardElevation = 4f
                                cardContainer.addView(cardView)

                                val textViewDireccion = TextView(this@TicketsPropietarios)
                                textViewDireccion.text = "$direccion"
                                val layoutParamsTextViewDireccion = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                layoutParamsTextViewDireccion.setMargins(
                                    206,
                                    8,
                                    16,
                                    8
                                ) // Establece los márgenes deseados en píxeles
                                textViewDireccion.layoutParams = layoutParamsTextViewDireccion
                                cardView.addView(textViewDireccion)
                                entrada = convertirTimestampAFechaHora(entrada)
                                val textViewEntrada = TextView(this@TicketsPropietarios)
                                textViewEntrada.text = "Entrada: $entrada"
                                val layoutParamsTextViewEntrada = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                layoutParamsTextViewEntrada.setMargins(
                                    206,
                                    80,
                                    6,
                                    8
                                ) // Establece los márgenes deseados en píxeles
                                textViewEntrada.layoutParams = layoutParamsTextViewEntrada
                                cardView.addView(textViewEntrada)
                                salida = convertirTimestampAFechaHora(salida)
                                val textViewSalida = TextView(this@TicketsPropietarios)
                                textViewSalida.text = "Salida: $salida"
                                val layoutParamsTextViewSalida = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                layoutParamsTextViewSalida.setMargins(
                                    206,
                                    160,
                                    16,
                                    8
                                ) // Establece los márgenes deseados en píxeles
                                textViewSalida.layoutParams = layoutParamsTextViewSalida
                                cardView.addView(textViewSalida)

                                val textViewStatus = TextView(this@TicketsPropietarios)
                                textViewStatus.text = "Status: $estatus"
                                val layoutParamsTextViewStatus = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                layoutParamsTextViewStatus.setMargins(
                                    206,
                                    240,
                                    16,
                                    8
                                ) // Establece los márgenes deseados en píxeles
                                textViewStatus.layoutParams = layoutParamsTextViewStatus
                                cardView.addView(textViewStatus)

                                val imageViewHouse = ImageView(this@TicketsPropietarios)
                                imageViewHouse.setImageResource(R.drawable.linehomep) // Establece la imagen a través de su ID de recurso
                                val layoutParamsHouse = ViewGroup.MarginLayoutParams(200, 200)
                                layoutParamsHouse.setMargins(10, 20, 30, 40)
                                imageViewHouse.layoutParams = layoutParamsHouse
                                cardView.addView(imageViewHouse)
                                if (estatus == "pendiente"){
                                    val llegoButton = Button(this@TicketsPropietarios)
                                    llegoButton.text = "Llegó"
                                    val colorverde = 0xFF609E43.toInt()
                                    val layoutParamsbotonllego = ViewGroup.MarginLayoutParams(200, 100)
                                    layoutParamsbotonllego.setMargins(200, 320, 30, 40)
                                    llegoButton.layoutParams = layoutParamsbotonllego
                                    llegoButton.setBackgroundColor(colorverde)
                                    llegoButton.setTextColor(Color.parseColor("#FFFFFF"))
                                    cardView.addView(llegoButton)
                                    llegoButton.setOnClickListener {
                                        val client = OkHttpClient()

                                        // Parámetros
                                        val formBody = FormBody.Builder()
                                            .add("ticket_id", id)
                                            .build()

                                        // Enlace y método
                                        val request = Request.Builder()
                                            .url("https://scientific-machine-production.up.railway.app/cursar_ticket")
                                            .post(formBody)
                                            .build()
                                        // Corrutinado (ejecución)
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val response = client.newCall(request).execute()
                                                val responseData = response.body?.string()
                                                withContext(Dispatchers.Main) {
                                                    if (response.isSuccessful) {

                                                        // La solicitud fue exitosa (código de respuesta en el rango 200-299)
                                                        intent.putExtra("message", "Ticket Borrado")

                                                        startActivity(intentTickets)
                                                    } else {
                                                        val json = JSONObject(responseData)
                                                        val errorMessage = json.optString(
                                                            "error",
                                                            "Error desconocido"
                                                        )
                                                        // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                                                        mostrarAlerta(errorMessage)
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
                                if (estatus == "encurso"){
                                    val salioButton = Button(this@TicketsPropietarios)
                                    salioButton.text = "Salió"
                                    val colorverde = 0xFF000000.toInt()
                                    val layoutParamsbotonsalio = ViewGroup.MarginLayoutParams(200, 100)
                                    layoutParamsbotonsalio.setMargins(300, 320, 30, 40)
                                    salioButton.layoutParams = layoutParamsbotonsalio
                                    salioButton.setBackgroundColor(colorverde)
                                    salioButton.setTextColor(Color.parseColor("#FFFFFF"))
                                    cardView.addView(salioButton)
                                    salioButton.setOnClickListener {
                                        val client = OkHttpClient()

                                        // Parámetros
                                        val formBody = FormBody.Builder()
                                            .add("ticket_id", id)
                                            .build()

                                        // Enlace y método
                                        val request = Request.Builder()
                                            .url("https://scientific-machine-production.up.railway.app/finalizar_ticket")
                                            .post(formBody)
                                            .build()
                                        // Corrutinado (ejecución)
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val response = client.newCall(request).execute()
                                                val responseData = response.body?.string()
                                                withContext(Dispatchers.Main) {
                                                    if (response.isSuccessful) {

                                                        // La solicitud fue exitosa (código de respuesta en el rango 200-299)
                                                        intent.putExtra("message", "Ticket Finalizado")

                                                        startActivity(intentTickets)
                                                    } else {
                                                        val json = JSONObject(responseData)
                                                        val errorMessage = json.optString(
                                                            "error",
                                                            "Error desconocido"
                                                        )
                                                        // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                                                        mostrarAlerta(errorMessage)
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

                                if (estatus != "cancelado" && estatus != "finalizado") {
                                    val cancelButton = Button(this@TicketsPropietarios)
                                    cancelButton.text = "Cancelar"
                                    val colorRojoOscuro = 0xFFC33838.toInt()
                                    val layoutParamsboton = ViewGroup.MarginLayoutParams(200, 100)
                                    layoutParamsboton.setMargins(600, 320, 30, 40)
                                    cancelButton.layoutParams = layoutParamsboton
                                    cancelButton.setBackgroundColor(colorRojoOscuro)
                                    cancelButton.setTextColor(Color.parseColor("#FFFFFF"))
                                    cardView.addView(cancelButton)
                                    cancelButton.setOnClickListener {
                                        val client = OkHttpClient()

                                        // Parámetros
                                        val formBody = FormBody.Builder()
                                            .add("ticket_id", id)
                                            .build()

                                        // Enlace y método
                                        val request = Request.Builder()
                                            .url("https://scientific-machine-production.up.railway.app/cancelar_ticket")
                                            .post(formBody)
                                            .build()
                                        // Corrutinado (ejecución)
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val response = client.newCall(request).execute()
                                                val responseData = response.body?.string()
                                                withContext(Dispatchers.Main) {
                                                    if (response.isSuccessful) {

                                                        // La solicitud fue exitosa (código de respuesta en el rango 200-299)
                                                        intent.putExtra("message", "Ticket Borrado")

                                                        startActivity(intentTickets)
                                                    } else {
                                                        val json = JSONObject(responseData)
                                                        val errorMessage = json.optString(
                                                            "error",
                                                            "Error desconocido"
                                                        )
                                                        // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                                                        mostrarAlerta(errorMessage)
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
                        } else {

                            // La solicitud no fue exitosa (código de respuesta fuera del rango 200-299)
                            mostrarAlerta(responseData.toString())

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
        alertDialog.setTitle("Mensaje")
        alertDialog.setMessage(valor)
        alertDialog.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }

}