package com.example.recipeapp.Utility


import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView


import java.util.ArrayList

class SpinnerTool {

    fun setSpinner(context: Context, spinnerArray: Array<String>, spinner: Spinner) {
        val spinnerArrayAdapter = object : ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, spinnerArray) {

            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                tv.gravity = Gravity.CENTER

                tv.setTextColor(Color.BLACK)


                return view
            }

        }
        spinner.adapter = spinnerArrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItemText = parent.getItemAtPosition(position) as String
                // If user change the default selection
                // First item is disable and it is used for hint
                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    2 -> {
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }
    }


}
