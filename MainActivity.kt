package com.example.lab5

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvTimeSpent: TextView = findViewById(R.id.tvTimeSpent)

        tvTimeSpent.text = "Час проведений в телефоні від моменту останнього запуску: ${formatTime(getScreenTime())}"

    }

    private fun getScreenTime(): Long {
        val prefs = getSharedPreferences("ScreenTimePrefs", Context.MODE_PRIVATE)
        return prefs.getLong("todayScreenTime", 0)
    }

    private fun formatTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        return "$hours год $minutes хв"
    }
}