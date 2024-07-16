package com.example.hostelhub.warden

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hostelhub.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class WardenLogin : AppCompatActivity() {
    lateinit var userTxt: EditText
    lateinit var passTxt: EditText
    lateinit var loginBtn: Button
    private lateinit var mAuth: FirebaseAuth


    private lateinit var back_Btn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warden_login)
        userTxt = findViewById(R.id.user_edit_text_warden)
        passTxt = findViewById(R.id.password_edit_text_warden)
        loginBtn = findViewById(R.id.login_button_warden)
//        googleIn2=findViewById(R.id.google2)
//        val googleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("546368241928-v5ri0fiml1nu17v7k3t0qc9j7e938c5j.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//        googlesignInClient= GoogleSignIn.getClient(this,googleSignInOptions)
//        googleIn2.setOnClickListener{
//            val signInClient=googlesignInClient.signInIntent
//            launcher.launch(signInClient)
//        }

        val sharedPrefs=getSharedPreferences("warden_prefs",Context.MODE_PRIVATE)

        //check if he already logged in
        if(sharedPrefs.getBoolean("is_logged_in",false)){
            val intent=Intent(this,WardenDashboard::class.java)
            startActivity(intent)
            finish()
        }
        loginBtn.setOnClickListener(){
            val user=userTxt.text.toString()
            val pass=passTxt.text.toString()
            if(user=="admin" && pass=="admin"){

                //save flag indicating that the warden is logged in
                val editor:SharedPreferences.Editor=sharedPrefs.edit()
                editor.putBoolean("is_logged_in",true)
                editor.apply()

                startActivity(Intent(this,WardenDashboard::class.java))
                finish()
            }else{
                Toast.makeText(this, "Wrong Credentials Entered !!", Toast.LENGTH_SHORT).show()

            }
        }
        back_Btn = findViewById(R.id.btnBack)
        back_Btn.setOnClickListener(){
            onBackPressed()
            finish()
        }
    }

//    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            result->
//        if(result.resultCode== Activity.RESULT_OK){
//            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            if(task.isSuccessful){
//                val account: GoogleSignInAccount?=task.result
//                val credential= GoogleAuthProvider.getCredential(account?.idToken,null)
//                mAuth.signInWithCredential(credential).addOnCompleteListener{
//                    if(it.isSuccessful){
//                        startActivity(Intent(this, WardenDashboard::class.java))
//                    }else{
//                        Toast.makeText(this,"Login failed: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//            }
//        }else{
//            Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show()
//        }
//    }
}