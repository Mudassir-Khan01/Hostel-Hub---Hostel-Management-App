package com.example.hostelhub.Attendance.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelhub.Attendance.db.DbHelper1
import com.example.hostelhub.Attendance.db.Entity.QrResult
import com.example.hostelhub.Attendance.dialogs.QrCodeResultDialog
import com.example.hostelhub.Attendance.utils.toFormattedDisplay
import com.example.hostelhub.R
import java.util.Calendar

class ScannedResultListAdapter
    (var dbHelper1: DbHelper1, var context: Context,
     var listOfScannedResults:MutableList<QrResult>)
    :RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {

    private var resultDialog: QrCodeResultDialog = QrCodeResultDialog(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
    return ScannedResultListViewHolder(LayoutInflater.from(context).inflate(R.layout.layoyt_single_item_qr_result,parent,false))
    }

    override fun getItemCount(): Int {
return listOfScannedResults.size
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
     holder.bind(listOfScannedResults[position],position)
    }

    inner class ScannedResultListViewHolder(var view: View):RecyclerView.ViewHolder(view){

        fun bind(qrResult: QrResult,position: Int){
         view.findViewById<AppCompatTextView>(R.id.result).text=qrResult.result!!

            view.findViewById<AppCompatTextView>(R.id.tvTime).text=qrResult.calendar.toFormattedDisplay()
         onClicks(qrResult,position)
        }
        private fun onClicks(qrResult: QrResult,position: Int){
            view.setOnClickListener {
                resultDialog.show(qrResult)
            }

            view.setOnLongClickListener{
                showDeleteDialog(qrResult,position)
                return@setOnLongClickListener true
            }
        }
        private fun showDeleteDialog(qrResult: QrResult,position: Int){
            AlertDialog.Builder(context,R.style.CustomAlertDialog).setTitle(context.getString(R.string.delete))
                .setMessage(context.getString(R.string.want_to_delete))
                .setPositiveButton(context.getString(R.string.delete)){_,_->
                    deleteThisRecord(qrResult, position)
                }
                .setNegativeButton(context.getString(R.string.cancel)){dialog,_->
                    dialog.cancel()
                }.show()
        }
        private fun deleteThisRecord(qrResult: QrResult,position: Int){
            dbHelper1.deleteQrResult(qrResult.id!!)
            listOfScannedResults.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}