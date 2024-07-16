package com.example.hostelhub.Attendance.db.Convertor

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverters {
    @TypeConverter
    fun toCalendar(timestamp: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar): Long {
        return calendar.timeInMillis
    }
}