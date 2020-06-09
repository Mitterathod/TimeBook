package com.example.timebook

import java.text.SimpleDateFormat
import java.util.*

fun getPresentTime(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)

fun getPresentHour(): String {
    return SimpleDateFormat("HH", Locale.getDefault()).format(Calendar.getInstance().time)
}

fun getPresentMinute(): String {
    return SimpleDateFormat("mm", Locale.getDefault()).format(Calendar.getInstance().time)
}

fun getDate(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
}


