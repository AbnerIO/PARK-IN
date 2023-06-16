package com.example.parkin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PagoSolicitud : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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


                val format = SimpleDateFormat("HH:mm", Locale.US)

                val currentTime = Calendar.getInstance()
                currentTime.set(Calendar.HOUR_OF_DAY, format.parse(entrada)?.hours ?: 0)
                currentTime.set(Calendar.MINUTE, format.parse(entrada)?.minutes ?: 0)
                currentTime.set(Calendar.SECOND, 0)

                val entradaTimestamp = currentTime.timeInMillis / 1000

                currentTime.set(Calendar.HOUR_OF_DAY, format.parse(salida)?.hours ?: 0)
                currentTime.set(Calendar.MINUTE, format.parse(salida)?.minutes ?: 0)

                val salidaTimestamp = currentTime.timeInMillis / 1000

                println("Timestamp de entrada: $entradaTimestamp")
                println("Timestamp de salida: $salidaTimestamp")

            val btnPagoSolicitud = findViewById<Button>(R.id.btn_pagoSolicitud)
            btnPagoSolicitud.setOnClickListener {
                val intent = Intent(this, ADondeVamos::class.java)
                val client = OkHttpClient()
                println("entrada$entrada")
                println("spotid$spot_id")
                println("salida$salida")
                println("bicis$bicis")
                println("entrada$entrada")
                println("nota$nota")
                println("precio$precio")
                println("carros$carros")
                // Parámetros
                if (bicis != null && spot_id != null && carros != null && salida != null && entrada != null && nota != null) {
                    val btn_pagar = findViewById<Button>(R.id.btn_pagoSolicitud)
                    btn_pagar.setOnClickListener {
                        val client = OkHttpClient()

                        // Parámetros
                        val formBody = FormBody.Builder()
                            .add("carros", carros.toString())
                            .add("bicis", bicis.toString())
                            .add("spot_id", spot_id.toString())
                            .add("client_id", user_id.toString())
                            .add("hora_salida", salidaTimestamp.toString())
                            .add("hora_entrada", entradaTimestamp.toString())
                            .build()

                        // Enlace y método
                        val request = Request.Builder()
                            .url("$URL/solicitar_spot")
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
                                        intent.putExtra("message", "Ticket pagado, puede verlo en el apartado")
                                        startActivity(intent)
                                    } else {

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

                }else{

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
    fun mostrarAlerta(valor: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Mensaje")
        alertDialog.setMessage(valor)
        alertDialog.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }}
