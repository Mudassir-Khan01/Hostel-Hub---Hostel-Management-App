package com.example.hostelhub.warden

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hostelhub.R
import com.example.hostelhub.db.BookingDetails
import com.example.hostelhub.db.BookingDetailsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [BookingDetails::class], version = 1)
abstract class BookingDatabase:RoomDatabase(){
    abstract fun bookingDetailsDao():BookingDetailsDao
}

class CheckStudentDetails : AppCompatActivity() {
    private val PREF_NAME = "my_settings"
    private lateinit var sharedPreferences: SharedPreferences

    lateinit var res: TextView
    lateinit var deleteBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_student_details)
        res = findViewById(R.id.res)
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Database

        val db = Room.databaseBuilder(
            applicationContext,
            BookingDatabase::class.java,
            "booking-database"
        ).build()

        //retrieve data
        GlobalScope.launch (Dispatchers.IO){
            val allBookingDetails=db.bookingDetailsDao().getAllBookingDetails()

            //display
            launch(Dispatchers.Main) {
                var details=""
                for(booking in allBookingDetails){
//                    details+="Name: ${booking.nameOfStudent}\n" +
//                            "Reg. No.: ${booking.registrationNumber}\n" +
//                            "Email: ${booking.emailOfStudent}\n" +
//                            "Gender: ${booking.gender}\n" +
//                            "Hostel: ${booking.hostel}\n" +
//                            "Room Type: ${booking.roomType}\n" +
//                            "Seater: ${booking.seater}\n" +
//                            "Room: ${booking.room}\n" +
//                            "Bed: ${booking.bed}\n\n"
                    details+= """
              Name:         ${booking.nameOfStudent?.padEnd(20)}
              Reg. No.:     ${booking.registrationNumber?.padEnd(20)}
              Email:        ${booking.emailOfStudent?.padEnd(20)}
              Gender:       ${booking.gender?.padEnd(20)}
              Hostel:       ${booking.hostel?.padEnd(20)}
              Room Type:    ${booking.roomType?.padEnd(20)}
              Seater:       ${booking.seater?.padEnd(20)}
              Room:         ${booking.room?.padEnd(20)}
              Bed:          ${booking.bed?.padEnd(20)}
          
          
          
          """.trimIndent()
                }

//res.text = Html.fromHtml(infoText, Html.FROM_HTML_MODE_COMPACT)
// Set the right column color for each value

// Set the SpannableString to the TextView
                res.text = details
// Set text size, text style, and other properties
                res.textSize = 16f
                res.setTypeface(Typeface.MONOSPACE, Typeface.BOLD) // Set monospace font

            }
        }


        deleteBtn = findViewById(R.id.delete_button)
        deleteBtn.setOnClickListener() {
            GlobalScope.launch(Dispatchers.IO) {
                db.bookingDetailsDao().deleteAllBookingDetails()
            }

            val editor= sharedPreferences.edit()
            editor.putBoolean("roomType_selected", false)
            editor.putBoolean("seater_selected", false)
            editor.putBoolean("room_selected", false)
            editor.putBoolean("bed_selected", false)
            editor.apply()
            res.text = ""
            deleteBtn.isEnabled = false
        }
    }
}