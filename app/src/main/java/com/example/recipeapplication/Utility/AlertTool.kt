package com.example.recipeapp.Utility

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.recipeapplication.R

class AlertTool(private val mListener: alertListener) {


    fun alertSingleButton(context: Context, dialogTag: String, messageText: Int, positiveText: Int) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage(context.resources.getString(messageText))
        builder1.setCancelable(false)

        builder1.setPositiveButton(context.resources.getString(positiveText)) { dialog, id ->
            mListener.onPositiveDialogClick(dialogTag)
            dialog.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.colorPrimary))
    }

    fun alertDuoButton(context: Context, dialogTag: String, messageText: Int, positiveText: Int, negativeText: Int) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage(context.resources.getString(messageText))
        builder1.setCancelable(false)

        builder1.setPositiveButton(context.resources.getString(positiveText)) { dialog, id ->
            mListener.onPositiveDialogClick(dialogTag)
            dialog.cancel()
        }

        builder1.setNegativeButton(context.resources.getString(negativeText)) { dialog, id ->
            mListener.onNegativeDialogClick(dialogTag)
            dialog.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.colorPrimary))
        alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.colorPrimary))
    }

    interface alertListener {
        fun onPositiveDialogClick(dialogTag: String)
        fun onNegativeDialogClick(dialogTag: String)
    }
}
