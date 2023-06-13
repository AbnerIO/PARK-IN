package com.example.parkin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class PagoSolicitud : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_solicitud)
        var user_id=1
        //Botones principales
        val URL = "https://scientific-machine-production.up.railway.app"
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

        val extras = intent.extras
        if (extras != null) {
            val entrada = extras.getString("entrada")
            val spot_id = extras.getString("id")
            val salida = extras.getString("salida")
            val bicis = extras.getString("bicis")
            val carros = extras.getString("carros")
            val nota = extras.getString("nota")
            val precio = extras.getString("precio")
            println("entrada$entrada")
            println("spotid$spot_id")
            println("salida$salida")
            println("bicis$bicis")
            println("entrada$entrada")
            println("nota$nota")
            println("precio$precio")
            println("carros$carros")








            if (bicis != null && spot_id != null && carros != null && salida != null && entrada != null && nota != null) {
                mostrarAlerta("paso")
                val btn_pagar = findViewById<Button>(R.id.btn_pagar)
                btn_pagar.setOnClickListener {
                    val client = OkHttpClient()

                    // Parámetros
                    val formBody = FormBody.Builder()
                        .add("user_id", user_id.toString())
                        .add("entrada", entrada)
                        .add("salida", salida)
                        .add("spot_id", spot_id.toString())
                        .add("precio", precio.toString())
                        .add("nota", nota)
                        .build()

                    // Enlace y método
                    val request = Request.Builder()
                        .url("$URL/solicitar_spot/$spot_id")
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
                                    intent.putExtra("message", "Usuario Creado")
                                } else {
                                    val json = JSONObject(responseData)
                                    val errorMessage = json.optString("error", "Error desconocido")
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

            }else{
                mostrarAlerta("no paso")
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