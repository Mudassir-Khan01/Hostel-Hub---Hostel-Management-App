package com.example.hostelhub.student

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.hostelhub.R
import com.example.hostelhub.student.fragments.RoomType
import com.google.firebase.auth.FirebaseAuth

class StudentDashboard : AppCompatActivity() {
    companion object{
        lateinit var roomType : String
        lateinit var seater: String
        lateinit var gender: String
        var nameOfStudent: String? = ""
        var emailOfStudent: String? = ""
        var registrationNumber: String? = ""
        lateinit var hostel: String
        lateinit var room: String
        lateinit var bed: String
    }

    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "my_settings"
    private lateinit var logout_Btn : ImageView
    private lateinit var back_Btn : ImageView
    private lateinit var mAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        mAuth = FirebaseAuth.getInstance()

        logout_Btn = findViewById(R.id.logout_button)
        logout_Btn.setOnClickListener() {
            mAuth.signOut()
            startActivity(Intent(this, StudentLogin::class.java))
            finish()
        }
        back_Btn = findViewById(R.id.btnBack)
        back_Btn.setOnClickListener() {
            onBackPressed()
            finish()
        }


        //fragment

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Check if user has filled out details before
        if (sharedPreferences.getBoolean("is_logged_in", true) &&
            sharedPreferences.getBoolean("roomType_selected", true) &&
            sharedPreferences.getBoolean("seater_selected", true) &&
            sharedPreferences.getBoolean("room_selected", true) &&
            sharedPreferences.getBoolean("bed_selected", true)
        ) {
            // If all required details are filled, navigate directly to StudentsSelectedDetails activity
            startActivity(Intent(this, StudentsSelectedDetails::class.java))
            finish()
        } else {
            // If details are not filled, navigate to RoomType fragment to start filling details
            val roomTypeFragment = RoomType()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, roomTypeFragment)
                .commit()
        }
    }
    }
