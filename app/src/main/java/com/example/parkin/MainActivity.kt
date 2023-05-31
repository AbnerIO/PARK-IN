package com.example.parkin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.android.volley.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val URL = "http://192.168.0.7:5000"
        println(URL)
        val btnRegistrarse = findViewById<Button>(R.id.btn_registrarse)
        val btnLogin = findViewById<Button>(R.id.btn_login)


        // Cosas heredadas
        val extras = intent.extras
        if (extras != null) {
            val message =
                extras.getString("message") // Obtiene el valor del extra utilizando la clave
            if (message != null) {
                mostrarAlerta(message)
            }
        }


        // Acciones
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intentCliente = Intent(this, ADondeVamos::class.java)
            val intentPropietario = Intent(this, MisSpots::class.java)
            val email = findViewById<EditText>(R.id.input_email)
            val password = findViewById<EditText>(R.id.input_password)


            val client = OkHttpClient()

            // Parámetros
            val formBody = FormBody.Builder()
                .add("email", email.text.toString())
                .add("password", password.text.toString())
                .build()

            // Enlace y método
            val request = Request.Builder()
                .url("$URL/login")
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
                            val json = JSONObject(responseData)
                            val rol = json.getString("rol_de_usuario")
                            if (rol == "Cliente") {
                                startActivity(intentCliente)
                            } else {
                                startActivity(intentPropietario)
                            }

                            // startActivity(intent)
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

    }

    // Funcion imprimir alerta en pantalla
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
