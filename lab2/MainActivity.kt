package com.example.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), InputFragment.OnInputListener, ResultFragment.OnCancelListener {

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
}