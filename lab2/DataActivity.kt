package com.example.lab2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val dataTextView = findViewById<TextView>(R.id.dataTextView)

        // Зчитування даних із файлу
        val file = File(filesDir, "data.txt")
        if (file.exists()) {
            val data = file.readText()
            dataTextView.text = if (data.isNotEmpty()) data else "Дані відсутні"
        } else {
            dataTextView.text = "Дані відсутні"
        }
    }
}