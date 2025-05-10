package com.example.lab2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {

    private lateinit var listener: OnInputListener

    interface OnInputListener {
        fun onInputSubmit(phoneType: String, brand: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnInputListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val phoneTypeSpinner = view.findViewById<Spinner>(R.id.phoneTypeSpinner)
        val brandRadioGroup = view.findViewById<RadioGroup>(R.id.brandRadioGroup)
        val okButton = view.findViewById<Button>(R.id.okButton)
        val openButton = view.findViewById<Button>(R.id.openButton)

        okButton.setOnClickListener {
            val phoneType = phoneTypeSpinner.selectedItem.toString()
            val brandId = brandRadioGroup.checkedRadioButtonId
            val brand = when (brandId) {
                R.id.samsungRadioButton -> "Samsung"
                R.id.appleRadioButton -> "Apple"
                R.id.xiaomiRadioButton -> "Xiaomi"
                else -> ""
            }
            listener.onInputSubmit(phoneType, brand)
        }

        openButton.setOnClickListener {
            (activity as? MainActivity)?.openDataActivity()
        }

        return view
    }
}