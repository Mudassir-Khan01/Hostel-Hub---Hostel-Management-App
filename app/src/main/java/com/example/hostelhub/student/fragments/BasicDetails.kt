package com.example.hostelhub.student.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hostelhub.R
import com.example.hostelhub.student.StudentDashboard


class BasicDetails : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    private val PREF_NAME = "my_settings"

    lateinit var viewBasic: View
    lateinit var rgrp : RadioGroup
    lateinit var nextButton: Button

    lateinit var nameEdtTxt: EditText
    lateinit var regEdtTxt: EditText
    lateinit var emailEdtTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBasic=inflater.inflate(R.layout.fragment_basic_details,container,false)
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        //displaying details
        nameEdtTxt = viewBasic.findViewById(R.id.name_edit_text)
        emailEdtTxt = viewBasic.findViewById(R.id.email_edit_text)
        regEdtTxt = viewBasic.findViewById(R.id.registration_edit_text)

        rgrp = viewBasic.findViewById(R.id.rgrp)

        nextButton = viewBasic.findViewById(R.id.next_button)
        nextButton.setOnClickListener(){
            var selectId=rgrp.checkedRadioButtonId
            if(selectId==-1){
                Toast.makeText(requireContext(), "Please select any one!!", Toast.LENGTH_SHORT)
                    .show()
            }else{
                val rB=rgrp.findViewById<RadioButton>(selectId)
                StudentDashboard.gender = rB.text.toString()
                StudentDashboard.nameOfStudent = nameEdtTxt.text.toString()
                StudentDashboard.emailOfStudent = emailEdtTxt.text.toString()
                StudentDashboard.registrationNumber = regEdtTxt.text.toString()
                with(sharedPreferences.edit()) {
                    putString("name", nameEdtTxt.text.toString())
                    putString("email", emailEdtTxt.text.toString())
                    putString("reg", regEdtTxt.text.toString())
                    putString("gender", rB.text.toString())
                    apply()
                }

                //change fragment
                val hostel=Hostel()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,hostel).commit()
            }
        }
        return viewBasic
    }

}