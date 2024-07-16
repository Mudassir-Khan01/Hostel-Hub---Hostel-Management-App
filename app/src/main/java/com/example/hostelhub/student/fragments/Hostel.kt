package com.example.hostelhub.student.fragments

import android.content.Context
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


class Hostel : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "my_settings"
    lateinit var viewHostel: View
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
        viewHostel = inflater.inflate(R.layout.fragment_hostel, container, false)

        rgrp = viewHostel.findViewById(R.id.rgrp)

        nextButton = viewHostel.findViewById(R.id.next_button)
        nextButton.setOnClickListener() {
            var selectId = rgrp.checkedRadioButtonId
            if (selectId == -1) {
                Toast.makeText(requireContext(), "Please select any one!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
val rB=rgrp.findViewById<RadioButton>(selectId)
                StudentDashboard.hostel = rB.text.toString()
                sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("hostel", StudentDashboard.hostel)
                    putBoolean("hostel_selected", true) // Mark that hostel is selected
                    apply()
                }
                // change fragment
                val room = Room()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,room).commit()
            }
        }

        return viewHostel
    }


}