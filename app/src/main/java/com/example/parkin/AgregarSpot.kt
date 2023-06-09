package com.example.parkin

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest
import android.graphics.Bitmap
import android.util.Base64
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream



class AgregarSpot : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_spot)

        val URL = "https://scientific-machine-production.up.railway.app"
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

        val CAMERA_REQUEST_CODE = 123
        val btnFoto = findViewById<Button>(R.id.btn_SubirComprobante)
        btnFoto.setOnClickListener{
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
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
                    .url("$URL/spots/add")
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

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            123
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Crear un archivo en la carpeta DCIM
            val displayName = "nombre_imagen.jpg"
            val imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + displayName
            val imageFile = File(imagePath)

            try {
                // Escribir el contenido de la imagen en el archivo
                val stream = ByteArrayOutputStream()
                val outputStream = FileOutputStream(imageFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                // Actualizar la galería con la nueva imagen
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = Uri.fromFile(imageFile)
                mediaScanIntent.data = contentUri
                sendBroadcast(mediaScanIntent)
                val byteArray = stream.toByteArray()
                val cliente = OkHttpClient()
                val URL = "https://scientific-machine-production.up.railway.app"
//
                CoroutineScope(Dispatchers.IO).launch {
                    val file = File.createTempFile("imagen", ".jpg")
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(byteArray)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    val requestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("imagen", "imagen.jpg", file.asRequestBody("image/jpeg".toMediaType()))
                        .build()

                    val request = Request.Builder()
                        .url("$URL/foto")
                        .post(requestBody)
                        .build()

                    try {
                        val response = withContext(Dispatchers.IO) {
                            cliente.newCall(request).execute()
                        }

                        val responseData = response.body?.string()

                        if (response.isSuccessful) {

                            println(responseData)
                        } else {

                            println(responseData)
                        }
                    } catch (e: Exception) {

                        println(e.message)
                        // Manejar excepciones
                    }
                }

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                // Mostrar mensaje de error
                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

}