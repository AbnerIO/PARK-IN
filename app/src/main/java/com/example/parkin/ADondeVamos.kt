package com.example.parkin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import java.io.IOError
import java.io.IOException

class ADondeVamos : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adonde_vamos)
        //Botones principales
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


        val URL = "https://scientific-machine-production.up.railway.app"

        val btnBuscar = findViewById<Button>(R.id.btn_buscar)
        btnBuscar.setOnClickListener {

            val estado = findViewById<TextView>(R.id.estado).text.toString()
            val ciudad = findViewById<TextView>(R.id.ciudad).text.toString()
            val colonia = findViewById<TextView>(R.id.colonia).text.toString()
            val intentVistaOpciones = Intent(this, VistaOpciones::class.java)
            val extras = Bundle()

            extras.putString("estado", estado)
            extras.putString("ciudad", ciudad)
            extras.putString("colonia", colonia)

            intentVistaOpciones.putExtras(extras)
            startActivity(intentVistaOpciones)
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