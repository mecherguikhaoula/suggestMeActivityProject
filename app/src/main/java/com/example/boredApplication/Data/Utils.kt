package com.example.boredApplication.Data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

/**
 * class to define generic methods
 */
class Utils {
    companion object {
        /**
         * Display a personilezed Alert
         */
        fun displayAlert(
            context: Context,
            method: () -> Unit,
            title: String?,
            positiveBotton: String,
            negativButton: String?
        ) {
            var validButton: Button
            var cancelButton: Button
            val customAlertDialog = MaterialAlertDialogBuilder(context)

            customAlertDialog.setMessage(title)
            customAlertDialog.setNegativeButton(negativButton, null)
            customAlertDialog.setPositiveButton(positiveBotton, null)
            customAlertDialog.setCancelable(false)
            alertDialog = customAlertDialog.create()
            alertDialog.setOnShowListener {
                validButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                validButton.setOnClickListener {
                    method()
                    alertDialog.dismiss()

                }
                cancelButton.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
            alertDialog.show()
        }

        fun getRandomColor(): Int {
            val rnd = Random()

            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

        lateinit var alertDialog: AlertDialog
    }
}