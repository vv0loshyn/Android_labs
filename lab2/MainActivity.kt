package com.example.lab2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity(), InputFragment.OnInputListener, ResultFragment.OnCancelListener {

    private val filename = "data.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, InputFragment())
                .commit()
        }
    }

    override fun onInputSubmit(phoneType: String, brand: String) {
        val result = "Тип телефону: $phoneType\nФірма: $brand"

        // Запис у файл
        val fileOutput = openFileOutput(filename, Context.MODE_APPEND)
        fileOutput.write((result + "\n").toByteArray())
        fileOutput.close()

        // Повідомлення про успішність запису
        Toast.makeText(this, "Дані збережено успішно", Toast.LENGTH_SHORT).show()

        // Показати результат
        val resultFragment = ResultFragment().apply {
            arguments = Bundle().apply {
                putString("result", result)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCancel() {
        supportFragmentManager.popBackStack()
    }

    // Метод для відкриття іншої активності
    fun openDataActivity() {
        val intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }
}