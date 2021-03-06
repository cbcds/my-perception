package com.cbcds.myperception.screens

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(private val onDateSetListener: DatePickerDialog.OnDateSetListener) :
    DialogFragment() {
    companion object {
        const val TAG = "datePicker"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            onDateSetListener,
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        return datePickerDialog
    }
}