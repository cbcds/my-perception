package com.cbcds.myperception.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private const val FORMAT = "dd.MM.yy"

        fun toString(year: Int, month: Int, dayOfMonth: Int): String {
            val calendar = Calendar.getInstance()
            with(calendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            return DateFormat.format(FORMAT, calendar).toString()
        }

        fun toDate(dateString: String): Date {
            return SimpleDateFormat(FORMAT, Locale.getDefault()).parse(dateString)!!
        }
    }
}