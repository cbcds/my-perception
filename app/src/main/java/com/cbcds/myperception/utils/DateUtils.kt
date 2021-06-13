package com.cbcds.myperception.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class DateUtils {
    companion object {
        fun dateToString(date: Date): String {
            return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        }

        fun dateToString(year: Int, month: Int, dayOfMonth: Int): String {
            return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .format(LocalDate.of(year, month, dayOfMonth))
        }

        fun stringToDate(dateString: String): Date {
            val localDate = LocalDate.from(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).parse(dateString)
            )
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        }
    }
}