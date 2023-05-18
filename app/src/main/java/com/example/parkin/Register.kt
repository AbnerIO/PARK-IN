package com.example.parkin


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity


class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegistrarme= findViewById<Button>(R.id.btn_registrarme)
        btnRegistrarme.setOnClickListener {
            val intent = Intent(this, MisSpots::class.java)
            startActivity(intent)
        }

    }
}