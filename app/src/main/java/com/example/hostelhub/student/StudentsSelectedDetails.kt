package com.example.hostelhub.student

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hostelhub.Attendance.MainActivity_2
import com.example.hostelhub.R
import com.example.hostelhub.db.BookingDetails
import com.example.hostelhub.db.BookingDetailsDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [BookingDetails::class], version = 1)
abstract class BookingDatabase:RoomDatabase(){
    abstract fun bookingDetailsDao():BookingDetailsDao
}

class StudentsSelectedDetails : AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "my_settings"

    lateinit var res: TextView
    lateinit var deleteBtn: Button
    private lateinit var logout_Btn2 : ImageView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var noticeview:CardView
    private lateinit var attendace:CardView


    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_selected_details)

        mAuth = FirebaseAuth.getInstance()

        logout_Btn2=findViewById(R.id.logout_button2)
        logout_Btn2.setOnClickListener() {
            mAuth.signOut()
            val editor= sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()
            startActivity(Intent(this, StudentLogin::class.java))
            finish()

        }

        noticeview=findViewById(R.id.Notice2)
        noticeview.setOnClickListener{
            startActivity(Intent(this,NoticeView::class.java))
            finish()
        }

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //    val editor=getSharedPreferences("my_settings", MODE_PRIVATE).edit()
//        editor.putString("roomtype",roomType)
//        editor.putString("seater",seater)
//        editor.putString("gender",gender)
//        editor.putString("name",name)
//        editor.putString("email",email)
//        editor.putString("reg",reg)
//        editor.putString("hostel",hostel)
//        editor.putString("room",room)
//        editor.putString("bed",bed)
//        editor.apply()

        val name = sharedPreferences.getString("name", "")
        val reg = sharedPreferences.getString("reg", "")
        val email = sharedPreferences.getString("email", "")
        val gender = sharedPreferences.getString("gender", "")
        val hostel = sharedPreferences.getString("hostel", "")
        val roomType = sharedPreferences.getString("roomType", "")
        val seater = sharedPreferences.getString("seater", "")
        val room = sharedPreferences.getString("room", "")
        val bed = sharedPreferences.getString("bed", "")
//        var roomType = intent.getStringExtra("roomType")
//        var seater = intent.getStringExtra("seater")
//        var gender = intent.getStringExtra("gender")
//        var name = intent.getStringExtra("name")
//        var email = intent.getStringExtra("email")
//        var reg = intent.getStringExtra("reg")
//        var hostel = intent.getStringExtra("hostel")
//        var room = intent.getStringExtra("room")
//        var bed = intent.getStringExtra("bed")

//        with(sharedPreferences.edit()) {
//            putString(KEY_ROOM_TYPE, roomType)
//            putString(KEY_SEATER, seater)
//            putString(KEY_GENDER, gender)
//            putString(KEY_NAME, name)
//            putString(KEY_EMAIL, email)
//            putString(KEY_REG, reg)
//            putString(KEY_HOSTEL, hostel)
//            putString(KEY_ROOM, room)
//            putString(KEY_BED, bed)
//            apply()
//        }
      //  displayDataFromSharedPreferences()

        val res = findViewById<TextView>(R.id.res)
        val infoText = """
    Name:         ${name?.padEnd(20)}
    Reg. No.:     ${reg?.padEnd(20)}
    Email:        ${email?.padEnd(20)}
    Gender:       ${gender?.padEnd(20)}
    Hostel:       ${hostel?.padEnd(20)}
    Room Type:    ${roomType?.padEnd(20)}
    Seater:       ${seater?.padEnd(20)}
    Room:         ${room?.padEnd(20)}
    Bed:          ${bed?.padEnd(20)}
""".trimIndent()

// Create a SpannableString from the infoText
        val spannableString = SpannableString(infoText)

// Set the left column color for each label
        val leftColumnColor = Color.BLUE
        val labels = arrayOf("Name: ", "Reg. No.:", "Email:", "Gender:", "Hostel:", "Room Type:", "Seater:", "Room:", "Bed:")
        for (label in labels) {
            val startIndex = infoText.indexOf(label)
            val endIndex = startIndex + label.length
            spannableString.setSpan(ForegroundColorSpan(leftColumnColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

// Set the right column color for each value
        val rightColumnColor = Color.RED
        val lines = infoText.split("\n")
        for (line in lines) {
            val colonIndex = line.indexOf(':')+1
            if (colonIndex != -1 && colonIndex < line.length - 1) {
                val startIndex = colonIndex + 1
                val endIndex = line.length
                spannableString.setSpan(ForegroundColorSpan(rightColumnColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

// Set the SpannableString to the TextView
        res.text = spannableString

// Set text size, text style, and other properties
        res.textSize = 16f
        res.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)// Set monospace font
        val editor=sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.apply()


//        res = findViewById(R.id.res)
//        res.text = """Name : ${name}
//            |Reg. No.: ${reg}
//            |Email: ${email}
//            |Gender: ${gender}
//            |Hostel: ${hostel}
//            |Room Type: ${roomType}
//            |Seater: ${seater}
//            |Room: ${room} (Random)
//            |Bed: ${bed}
//        """.trimMargin()

        //database
        val bookingDetails = BookingDetails(
            registrationNumber = reg ?: "",
            roomType = roomType ?: "",
            seater = seater ?: "",
            gender = gender ?: "",
            nameOfStudent = name ?: "",
            emailOfStudent = email ?: "",
            hostel = hostel ?: "",
            room = room ?: "",
            bed = bed ?: ""
        )
        val db = Room.databaseBuilder(
            applicationContext,
            BookingDatabase::class.java,
            "booking-database"
        ).build()
        GlobalScope.launch(Dispatchers.IO) {
            db.bookingDetailsDao().insertBookingDetails(bookingDetails)
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

        attendace=findViewById(R.id.attendance)
        attendace.setOnClickListener {
            Handler().postDelayed({
                checkForPermission()
            },2000)
        }

    }

    private fun checkForPermission(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            goToAttendaceActivity()
    }else{
        requestThePermission()
        }

    }

    private fun requestThePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)
    }
    private fun goToAttendaceActivity() {
        startActivity(Intent(this,MainActivity_2::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goToAttendaceActivity()
            }else if(isPermanentlyDenied()){
                showGoToAppSettingsDialog()
            }else{
                requestThePermission()
            }
        }
    }
    private fun isPermanentlyDenied(): Boolean {
        return shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
    }

    private fun showGoToAppSettingsDialog() {
        AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .setTitle(getString(R.string.grant_permissions))
            .setMessage(getString(R.string.we_need_permission))
            .setPositiveButton(getString(R.string.grant)){_,_->
             goToAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)){
                    _,_-> run{
                        finish()
            }
            }.show()
    }

    private fun goToAppSettings() {
        val intent = Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }



    }
