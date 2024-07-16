package com.example.hostelhub.student

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelhub.NoticeScreen.NoticeAdapter
import com.example.hostelhub.NoticeScreen.NoticeAdapter2
import com.example.hostelhub.R
import com.example.hostelhub.databinding.ActivityAllNoticeBinding
import com.example.hostelhub.databinding.ActivityNoticeItemsBinding
import com.example.hostelhub.db.NoteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoticeView : AppCompatActivity(),NoticeAdapter2.OnItemClickListner {
    private val binding: ActivityAllNoticeBinding by lazy {
        ActivityAllNoticeBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference= FirebaseDatabase.getInstance().reference
        auth=FirebaseAuth.getInstance()
        recyclerView=binding.notesRV
        recyclerView.layoutManager= LinearLayoutManager(this)
        val currentuser=auth.currentUser
        currentuser?.let { user->
            val notereference=databaseReference.child("users").child(user.uid).child("notice")
            notereference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notelist= mutableListOf<NoteItem>()
                    for(nodeSnapshot in snapshot.children){
                        val note=nodeSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            notelist.add(it)
                        }
                    }
                    notelist.reverse()
                    val adapter= NoticeAdapter2(notelist,this@NoticeView)
                    recyclerView.adapter=adapter
                    Log.d("Allnotes","Data retrieved $notelist")
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        }
    }

    override fun onDeleteClick(noteId: String) {


    }

    override fun onUpdateCLick(noteId: String, title: String, description: String) {

    }
}