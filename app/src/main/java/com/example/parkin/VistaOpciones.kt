package com.example.parkin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
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

class VistaOpciones : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_opciones)
        val URL = "https://scientific-machine-production.up.railway.app"
        val intentPagoSolicitud = Intent(this, FormaSolicitud::class.java)
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

        // Heredadas
        val extras = intent.extras
        if (extras != null) {
            val estado = extras.getString("estado")
            val ciudad = extras.getString("ciudad")
            val colonia = extras.getString("colonia")

            if (estado != null && ciudad != null && colonia != null) {
                // peticion
                val client = OkHttpClient()
                // Enlace y método
                val request = Request.Builder()
                    .url("https://scientific-machine-production.up.railway.app/spots/get/$estado/$ciudad/$colonia")
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
                                // La solicitud fue exitosa (código de respuesta en el rango 200-299)

                                // Obtener el array "spots" del objeto JSON
                                val spotsArray = json.getJSONArray("spots")

                                // Recorrer el array de spots
                                for (i in 0 until spotsArray.length()) {

                                    // Obtener el objeto spot en la posición actual
                                    val spotObject = spotsArray.getJSONObject(i)

                                    // Acceder a los valores de las propiedades
                                    val street = spotObject.getString("street")
                                    val street_number = spotObject.getString("street_number")
                                    val neighborhood = spotObject.getString("neighborhood")
                                    val city = spotObject.getString("city")
                                    val car_spaces_availables =
                                        spotObject.getString("car_spaces_availables")
                                    val bicycle_spaces_availables =
                                        spotObject.getString("bicycle_spaces_availables")
                                    val bicycle_space_rent =
                                        spotObject.getString("bicycle_space_rent")
                                    val car_space_rent = spotObject.getString("car_space_rent")
                                    val id = spotObject.getString("id")
                                    val map_url=spotObject.getString("map_url")

                                    val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
                                    val cardView = CardView(this@VistaOpciones)
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


                                    val textViewDireccion = TextView(this@VistaOpciones)
                                    textViewDireccion.text = "$street $street_number $neighborhood"
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

                                    val textViewCarro = TextView(this@VistaOpciones)
                                    textViewCarro.text = "$car_spaces_availables"
                                    val layoutParamsTextViewCarro = ViewGroup.MarginLayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    layoutParamsTextViewCarro.setMargins(
                                        320,
                                        100,
                                        0,
                                        0
                                    ) // Establece los márgenes deseados en píxeles
                                    textViewCarro.layoutParams = layoutParamsTextViewCarro
                                    cardView.addView(textViewCarro)


                                    val textViewBici = TextView(this@VistaOpciones)
                                    textViewBici.text = "$bicycle_spaces_availables"
                                    val layoutParamsTextViewBici = ViewGroup.MarginLayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    layoutParamsTextViewBici.setMargins(
                                        520,
                                        100,
                                        0,
                                        0
                                    ) // Establece los márgenes deseados en píxeles
                                    textViewBici.layoutParams = layoutParamsTextViewBici
                                    cardView.addView(textViewBici)


                                    val textViewPrice = TextView(this@VistaOpciones)
                                    textViewPrice.text = "$$bicycle_space_rent-$$car_space_rent p/hr"
                                    val layoutParamsTextViewPrice = ViewGroup.MarginLayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    layoutParamsTextViewPrice.setMargins(
                                        720,
                                        100,
                                        0,
                                        0
                                    ) // Establece los márgenes deseados en píxeles
                                    textViewPrice.layoutParams = layoutParamsTextViewPrice
                                    cardView.addView(textViewPrice)


                                    val imageViewBike = ImageView(this@VistaOpciones)
                                    imageViewBike.setImageResource(R.drawable.linebikep) // Establece la imagen a través de su ID de recurso
                                    val layoutParamsimgBike = ViewGroup.MarginLayoutParams(100, 100)
                                    layoutParamsimgBike.setMargins(400, 80, 0, 0)
                                    imageViewBike.layoutParams = layoutParamsimgBike
                                    // Agrega el ImageView a tu diseño principal
                                    cardView.addView(imageViewBike)

                                    val imageViewCar = ImageView(this@VistaOpciones)
                                    imageViewCar.setImageResource(R.drawable.linesportcar2p) // Establece la imagen a través de su ID de recurso
                                    val layoutParamscar = ViewGroup.MarginLayoutParams(100, 100)
                                    layoutParamscar.setMargins(200, 80, 30, 40)
                                    imageViewCar.layoutParams = layoutParamscar
                                    cardView.addView(imageViewCar)

                                    val imageViewHouse = ImageView(this@VistaOpciones)
                                    imageViewHouse.setImageResource(R.drawable.linehomep) // Establece la imagen a través de su ID de recurso
                                    val layoutParamsHouse = ViewGroup.MarginLayoutParams(200, 200)
                                    layoutParamsHouse.setMargins(10, 20, 30, 40)
                                    imageViewHouse.layoutParams = layoutParamsHouse
                                    cardView.addView(imageViewHouse)

                                    val imageViewCoin = ImageView(this@VistaOpciones)
                                    imageViewCoin.setImageResource(R.drawable.linemoneydollarcoinp) // Establece la imagen a través de su ID de recurso
                                    val layoutParamsCoin = ViewGroup.MarginLayoutParams(100, 100)
                                    layoutParamsCoin.setMargins(600, 80, 30, 40)
                                    imageViewCoin.layoutParams = layoutParamsCoin
                                    cardView.addView(imageViewCoin)

                                    cardView.setOnClickListener {
                                        val extras = Bundle()

                                        extras.putString("map_url", map_url)
                                        extras.putString("id", id)
                                        extras.putString("bike_price", bicycle_space_rent)
                                        extras.putString("car_price", car_space_rent)
                                        extras.putString("bike_spaces", bicycle_spaces_availables)
                                        extras.putString("car_spaces", car_spaces_availables)

                                        intentPagoSolicitud.putExtras(extras)
                                        startActivity(intentPagoSolicitud)
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