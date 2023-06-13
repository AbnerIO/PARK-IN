package com.example.parkin


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import com.android.volley.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import org.json.JSONObject
import java.net.URL


class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val URL = "https://scientific-machine-production.up.railway.app"
        val intent = Intent(this, MainActivity::class.java)

        // Referencias a botones
        val btnRegistrarme = findViewById<Button>(R.id.btn_registrarme)
        val name = findViewById<EditText>(R.id.input_nombre)
        val password = findViewById<EditText>(R.id.input_contraseña)
        val email = findViewById<EditText>(R.id.input_correo)
        val radioGroupTipo = findViewById<RadioGroup>(R.id.radio_tipoCliente)
        val phone = findViewById<EditText>(R.id.input_telefono)
        var tipo = "Cliente"
        radioGroupTipo.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.opcion_cliente -> tipo = "Cliente"
                R.id.opcion_propietario -> tipo = "Propietario"
            }
        }

        // Acciones
        btnRegistrarme.setOnClickListener {

            val client = OkHttpClient()

            // Parámetros
            val formBody = FormBody.Builder()
                .add("name", name.text.toString())
                .add("email", email.text.toString())
                .add("password", password.text.toString())
                .add("type", tipo)
                .add("phone", phone.text.toString())
                .build()

            // Enlace y método
            val request = Request.Builder()
                .url("$URL/users/add")
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
                            startActivity(intent)
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
        alertDialog.setTitle("Mensaje")
        alertDialog.setMessage(valor)
        alertDialog.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        alertDialog.show()
    }
}