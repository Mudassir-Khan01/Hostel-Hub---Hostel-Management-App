package com.example.hostelhub.student

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hostelhub.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class StudentLogin : AppCompatActivity(), View.OnClickListener{
    private lateinit var googleIn:Button
    lateinit var googlesignInClient:GoogleSignInClient

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mEmailEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mLoginButton: Button
    private lateinit var tVRegister: TextView
    lateinit var back_Btn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)
        mAuth= FirebaseAuth.getInstance()
        mEmailEditText = findViewById(R.id.email_edit_text)
        mPasswordEditText = findViewById(R.id.password_edit_text)
        mLoginButton = findViewById(R.id.login_button)
        mLoginButton.setOnClickListener(this)

        googleIn=findViewById(R.id.google)
        val googleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("546368241928-v5ri0fiml1nu17v7k3t0qc9j7e938c5j.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googlesignInClient= GoogleSignIn.getClient(this,googleSignInOptions)
        googleIn.setOnClickListener{
            val signInClient=googlesignInClient.signInIntent
            launcher.launch(signInClient)
        }

        //check if user is already logged in
        val currentuser=mAuth.currentUser
        if(currentuser!=null){
            //redirect to main activity
            val intent=Intent(this,StudentDashboard::class.java)
            startActivity(intent)
            finish()
        }

        tVRegister=findViewById(R.id.textView_register)
        tVRegister.setOnClickListener(){
            startActivity(Intent(this,StudentRegister::class.java))
            finish()
        }
        back_Btn = findViewById(R.id.btnBack)
        back_Btn.setOnClickListener(){
            onBackPressed()
            finish()
        }
    }

    override fun onClick(view: View) {
      when(view.id){
          R.id.login_button->loginUser()
      }
    }
    private fun loginUser(){
        val email = mEmailEditText.text.toString().trim()
        val password = mPasswordEditText.text.toString().trim()

        if (email.isEmpty()) {
            mEmailEditText.error = "Email is required"
            mEmailEditText.requestFocus()
            return
        }

        if (password.isEmpty()) {
            mPasswordEditText.error = "Password is required"
            mPasswordEditText.requestFocus()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, StudentDashboard::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(applicationContext, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode== Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account: GoogleSignInAccount?=task.result
                val credential= GoogleAuthProvider.getCredential(account?.idToken,null)
                mAuth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                          Toast.makeText(this,"Welcome to the Dashboard",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext,StudentDashboard::class.java))
                    }else{
                        Toast.makeText(applicationContext,"Login failed: ${task.exception?.message}",Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }else{
            Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show()
        }
    }
}
