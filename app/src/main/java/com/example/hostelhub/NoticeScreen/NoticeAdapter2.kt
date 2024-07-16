package com.example.hostelhub.NoticeScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelhub.R
import com.example.hostelhub.databinding.ActivityNoticeItemsBinding
import com.example.hostelhub.db.NoteItem

class NoticeAdapter2(private val notice:List<NoteItem>, private val itemClickListner: OnItemClickListner):
    RecyclerView.Adapter<NoticeAdapter2.NoteViewHolder2>() {
    interface OnItemClickListner{
        fun onDeleteClick(noteId:String)
        fun onUpdateCLick(noteId: String,title:String,description:String)

    }

    class NoteViewHolder2(val binding: ActivityNoticeItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.title2.text=note.Notice
            binding.desc2.text=note.desc   }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder2 {
        val binding=
            ActivityNoticeItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder2, position: Int) {
        holder.itemView.findViewById<Button>(R.id.update).visibility = View.INVISIBLE
        holder.itemView.findViewById<Button>(R.id.delete1).visibility = View.INVISIBLE
        val note=notice[position]//if we use findwebyid the holder.textview.settext(datalist.get(position).data)
        holder.bind(note)//  retrieve


        holder.binding.update.setOnClickListener {
            itemClickListner.onUpdateCLick(note.noteId,note.Notice,note.desc)//now u can perform task ...its in all notes by implementing 2 new methods
        }
        holder.binding.delete1.setOnClickListener {
            itemClickListner.onDeleteClick(note.noteId)    }    }


    override fun getItemCount(): Int {
        return notice.size
    }

}