package com.example.hostelhub.Attendance.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}
fun Calendar.toFormattedDisplay(): String {

    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US)
    return simpleDateFormat.format(this.time)
}