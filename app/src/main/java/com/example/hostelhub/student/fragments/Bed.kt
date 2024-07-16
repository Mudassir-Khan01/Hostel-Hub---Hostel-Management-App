package com.example.hostelhub.student.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hostelhub.R
import com.example.hostelhub.student.StudentDashboard
import com.example.hostelhub.student.StudentsSelectedDetails

class Bed : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "my_settings"
    lateinit var viewBed: View
    lateinit var rgrp : RadioGroup
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBed =  inflater.inflate(R.layout.fragment_bed, container, false)
        rgrp = viewBed.findViewById(R.id.rgrp)
// radioButtons

        val rb2 = viewBed.findViewById<RadioButton>(R.id.rb2)
        val rb3 = viewBed.findViewById<RadioButton>(R.id.rb3)
        val rb4 = viewBed.findViewById<RadioButton>(R.id.rb4)
        when (StudentDashboard.seater) {
            "1 Seater" -> {
                rb2.isEnabled = false
                rb3.isEnabled = false
                rb4.isEnabled = false
            }
            "2 Seater" -> {
                rb3.isEnabled = false
                rb4.isEnabled = false
            }
            "3 Seater" -> {
                rb4.isEnabled = false
            }
        }

        nextButton=viewBed.findViewById(R.id.next_button)
        nextButton.setOnClickListener(){
            var selectId=rgrp.checkedRadioButtonId
            if (selectId == -1) {
                Toast.makeText(requireContext(), "Please select any one!!", Toast.LENGTH_SHORT)
                    .show()
            }else{
                val rB = rgrp.findViewById<RadioButton>(selectId)
                StudentDashboard.bed = rB.text.toString()
                // change fragment
                //val bed = Bed()
                //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,bed).commit()

//                val intent = Intent(requireContext(), StudentsSelectedDetails::class.java)
//                intent.putExtra("roomType", StudentDashboard.roomType)
//                intent.putExtra("seater", StudentDashboard.seater)
//                intent.putExtra("gender", StudentDashboard.gender)
//                intent.putExtra("name", StudentDashboard.nameOfStudent)
//                intent.putExtra("email", StudentDashboard.emailOfStudent)
//                intent.putExtra("reg", StudentDashboard.registrationNumber)
//                intent.putExtra("hostel", StudentDashboard.hostel)
//                intent.putExtra("room", StudentDashboard.room)
//                intent.putExtra("bed", StudentDashboard.bed)
                sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("bed", StudentDashboard.bed)
                    putBoolean("bed_selected", true) // Mark that bed is selected
                    apply()
                }
                val intent = Intent(requireContext(), StudentsSelectedDetails::class.java)

                startActivity(intent)
                requireActivity().finish()
            }
        }
return viewBed
    }


}