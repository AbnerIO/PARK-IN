package com.example.parkin

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
import androidx.core.view.marginLeft
import com.android.volley.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import org.json.JSONArray


class MisSpots : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_spots)
        val owner_id = 1
        val URL = "https://scientific-machine-production.up.railway.app"
        val intentDescripcionDelSpot = Intent(this, DescripcionDelSpot::class.java)
        // Funciones base
        val btnTicketsPropietarios = findViewById<ImageView>(R.id.btn_ticketsPropietario)
        btnTicketsPropietarios.setOnClickListener {
            val intent = Intent(this, TicketsPropietarios::class.java)
            startActivity(intent)
        }
        val btnPerfilPropietario = findViewById<ImageView>(R.id.btn_Profile)
        btnPerfilPropietario.setOnClickListener {
            val intentPerfilPropietario = Intent(this, MiPerfilPropietario::class.java)
            intentPerfilPropietario.putExtra("owner_id", owner_id)
            startActivity(intentPerfilPropietario)
        }


        // Cosas heredadas
        val extras = intent.extras
        if (extras != null) {
            val message =
                extras.getString("message") // Obtiene el valor del extra utilizando la clave
            if (message != null) {
                mostrarAlerta(message)
            }
        }


        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        // peticion
        val client = OkHttpClient()
        // Enlace y método
        val request = Request.Builder()
            .url("$URL/spots/get/$owner_id")
            .get()
            .build()
        // Corrutinado (ejecución)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val spotsArray = JSONArray(responseData)
                        // La solicitud fue exitosa (código de respuesta en el rango 200-299)

                        // Obtener el array "spots" del objeto JSON


                        // Recorrer el array de spots
                        for (i in 0 until spotsArray.length()) {

                            // Obtener el objeto spot en la posición actual
                            val spotObject = spotsArray.getJSONObject(i)

                            // Acceder a los valores de las propiedades
                            val street = spotObject.getString("street")
                            val street_number = spotObject.getString("street_number")
                            val neighborhood = spotObject.getString("neighborhood")
                            val car_spaces_availables =
                                spotObject.getString("car_spaces_availables")
                            val bicycle_spaces_availables =
                                spotObject.getString("bicycle_spaces_availables")
                            val car_spaces = spotObject.getString("car_spaces")
                            val bicycle_space_rent = spotObject.getString("bicycle_space_rent")
                            val car_space_rent = spotObject.getString("car_space_rent")
                            val bicycle_spaces = spotObject.getString("bicycle_spaces")

                            val id = spotObject.getString("id")


                            val textViewDireccion = TextView(this@MisSpots)
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


                            val cardView = CardView(this@MisSpots)
                            cardView.id = View.generateViewId()
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            layoutParams.setMargins(16, 16, 16, 16)
                            cardView.layoutParams = layoutParams
                            cardView.radius = 20f
                            cardView.cardElevation = 4f
                            cardView.addView(textViewDireccion)
                            cardContainer.addView(cardView)
                            cardView.setOnClickListener {
                                intentDescripcionDelSpot.putExtra("id", id)
                                startActivity(intentDescripcionDelSpot)
                            }


                            val imageViewHouse = ImageView(this@MisSpots)
                            imageViewHouse.setImageResource(R.drawable.linehomep) // Establece la imagen a través de su ID de recurso
                            val layoutParamshouse = ViewGroup.MarginLayoutParams(200, 200)
                            layoutParamshouse.setMargins(10, 20, 30, 40)
                            imageViewHouse.layoutParams = layoutParamshouse
                            // Agrega el ImageView a tu diseño principal
                            cardView.addView(imageViewHouse)


                            val imageViewCar = ImageView(this@MisSpots)
                            imageViewCar.setImageResource(R.drawable.linesportcar2p) // Establece la imagen a través de su ID de recurso
                            val layoutParamscar = ViewGroup.MarginLayoutParams(100, 100)
                            layoutParamscar.setMargins(200, 80, 30, 40)
                            imageViewCar.layoutParams = layoutParamscar
                            cardView.addView(imageViewCar)


                            val imageViewBike = ImageView(this@MisSpots)
                            imageViewBike.setImageResource(R.drawable.linebikep) // Establece la imagen a través de su ID de recurso
                            val layoutParamsimgBike = ViewGroup.MarginLayoutParams(100, 100)
                            layoutParamsimgBike.setMargins(400, 80, 0, 0)
                            imageViewBike.layoutParams = layoutParamsimgBike
                            // Agrega el ImageView a tu diseño principal
                            cardView.addView(imageViewBike)


                            val textViewCarros = TextView(this@MisSpots)
                            textViewCarros.text = "$car_spaces_availables/$car_spaces "
                            val layoutParamsTextViewCarros = ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParamsTextViewCarros.setMargins(
                                310,
                                100,
                                16,
                                8
                            ) // Establece los márgenes deseados en píxeles
                            textViewCarros.layoutParams = layoutParamsTextViewCarros
                            cardView.addView(textViewCarros)

                            val textViewbike = TextView(this@MisSpots)
                            textViewbike.text = "$bicycle_spaces_availables/$bicycle_spaces "
                            val layoutParamsTextViewbike = ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParamsTextViewbike.setMargins(
                                500,
                                100,
                                16,
                                8
                            ) // Establece los márgenes deseados en píxeles
                            textViewbike.layoutParams = layoutParamsTextViewbike
                            cardView.addView(textViewbike)


                            val textViewprice = TextView(this@MisSpots)
                            textViewprice.text = "$$bicycle_space_rent-$$car_space_rent p/hr"
                            val layoutParamsTextViewprice = ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParamsTextViewprice.setMargins(
                                600,
                                100,
                                16,
                                8
                            ) // Establece los márgenes deseados en píxeles
                            textViewprice.layoutParams = layoutParamsTextViewprice
                            cardView.addView(textViewprice)

                        }
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
