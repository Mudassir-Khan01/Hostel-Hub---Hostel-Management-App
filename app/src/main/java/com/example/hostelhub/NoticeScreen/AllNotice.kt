package com.example.hostelhub.NoticeScreen

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelhub.R
import com.example.hostelhub.databinding.ActivityAllNoticeBinding
import com.example.hostelhub.databinding.ActivityDialogUpdateNoticeBinding
import com.example.hostelhub.db.NoteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNotice : AppCompatActivity(),NoticeAdapter.OnItemClickListner {
    private val binding:ActivityAllNoticeBinding by lazy {
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
                    val adapter=NoticeAdapter(notelist,this@AllNotice)
                    recyclerView.adapter=adapter
                    Log.d("Allnotes","Data retrieved $notelist")
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        }
    }

    override fun onDeleteClick(noteId: String) {
        val currentuser=auth.currentUser
        currentuser?.let { user->
            val notereference=databaseReference.child("users").child(user.uid).child("notice")
            notereference.child(noteId).removeValue()
        }
    }

    override fun onUpdateCLick(noteId: String, currentTitle: String, currentDescription: String) {
        val dialogBinding=ActivityDialogUpdateNoticeBinding.inflate(LayoutInflater.from(this))
        val dialog= AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Notice")
            .setPositiveButton("Update"){dialog,_->
                val newTitle=dialogBinding.updateTitle.text.toString()
                val newdescription=dialogBinding.updateDesc.text.toString()
                updateNoteDatabase(newTitle,newdescription,noteId)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitle.setText(currentTitle)
        dialogBinding.updateDesc.setText(currentDescription)
        dialog.show()
    }

    private fun updateNoteDatabase( newTitle: String, newdescription: String,noteId: String) {
        val currentuser=auth.currentUser
        currentuser?.let { user->
            val noteReference=databaseReference.child("users").child(user.uid).child("notice")
            val updateNote=NoteItem(newTitle,newdescription,noteId)
            noteReference.child(noteId).setValue(updateNote)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Notice Updated successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Failed to  Update Notice", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }


}

