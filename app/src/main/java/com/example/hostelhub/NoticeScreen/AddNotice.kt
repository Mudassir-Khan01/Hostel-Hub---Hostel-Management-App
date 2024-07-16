package com.example.hostelhub.NoticeScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hostelhub.R
import com.example.hostelhub.databinding.ActivityAddNoticeBinding
import com.example.hostelhub.db.NoteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNotice : AppCompatActivity() {
    private val binding:ActivityAddNoticeBinding by lazy{
        ActivityAddNoticeBinding.inflate(layoutInflater)
    }
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseRef= FirebaseDatabase.getInstance().reference
        auth=FirebaseAuth.getInstance()

        binding.post.setOnClickListener {
            val title=binding.title1.text.toString()
            val description=binding.description.text.toString()

            if(title.isEmpty()&&description.isEmpty()){
                Toast.makeText(this,"Fill the field", Toast.LENGTH_SHORT).show()
            }else{
                val currentUser=auth.currentUser
                currentUser?.let { user->
                    val notekey=databaseRef.child("users").child(user.uid).child("notice").push().key
                    val NoticeItem= NoteItem(title,description,notekey?:"")
                    if(notekey!=null){
                        databaseRef.child("users").child(user.uid).child("notice").child(notekey).setValue(NoticeItem)
                            .addOnCompleteListener{task->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                                }
                            }
                    }else{
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        binding.noticeboard.setOnClickListener{
            startActivity(Intent(this,AllNotice::class.java))
            finish()
        }
    }
}