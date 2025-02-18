package com.example.lab2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {

    private lateinit var listener: OnCancelListener

    interface OnCancelListener {
        fun onCancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnCancelListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        val result = arguments?.getString("result")
        resultTextView.text = result

        cancelButton.setOnClickListener {
            listener.onCancel()
        }

        return view
    }
}