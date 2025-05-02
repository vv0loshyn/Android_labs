package com.example.lab5

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var levelView: LevelView
    private var pitch: Float = 0f
    private var roll: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        levelView = LevelView(this)
        setContentView(levelView)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = it.values[0]
                val y = it.values[1]

                pitch = Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble())).toFloat()
                roll = Math.toDegrees(Math.atan2(-x.toDouble(), y.toDouble())).toFloat()

                levelView.invalidate()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private inner class LevelView(context: Context) : View(context) {
        private val paintLine: Paint = Paint().apply {
            color = Color.RED
            strokeWidth = 10f
        }
        private val paintText: Paint = Paint().apply {
            color = Color.BLACK
            textSize = 60f
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val width = width
            val height = height

            val centerX = width / 2f
            val centerY = height / 2f
            val endX = (centerX + Math.cos(Math.toRadians(roll.toDouble())) * width / 3).toFloat()
            val endY = (centerY + Math.sin(Math.toRadians(roll.toDouble())) * width / 3).toFloat()

            canvas.drawLine(centerX - (endX - centerX), centerY - (endY - centerY), endX, endY, paintLine)
            canvas.drawText("Кут нахилу: ${"%.1f".format(roll)}°", 50f, height - 100f, paintText)
        }
    }
}