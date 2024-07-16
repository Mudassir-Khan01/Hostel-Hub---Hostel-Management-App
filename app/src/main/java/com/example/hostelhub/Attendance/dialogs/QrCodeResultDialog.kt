package com.example.hostelhub.Attendance.dialogs

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.hostelhub.Attendance.db.DbHelper
import com.example.hostelhub.Attendance.db.DbHelper1
import com.example.hostelhub.Attendance.db.Entity.QrResult
import com.example.hostelhub.Attendance.db.database.QrResultDatabase
import com.example.hostelhub.Attendance.utils.toFormattedDisplay
import com.example.hostelhub.R

class QrCodeResultDialog(var context:Context) {
private lateinit var dialog: Dialog
private var qrResult:QrResult?=null
private var onDismissListener:OnDismissListener?=null
    private lateinit var dbHelperI:DbHelper1


    init {
        init()
        initDialog()
    }
    private fun init() {
        dbHelperI = DbHelper(QrResultDatabase.getAppDatabase(context)!!)
    }
    private fun initDialog(){
        dialog=Dialog(context)
        dialog.setContentView(R.layout.layout_qr_result_show)
        dialog.setCancelable(false)
        onClicks()
    }

    fun show(qrResult: QrResult){
        this.qrResult=qrResult
        dialog.findViewById<AppCompatTextView>(R.id.scannedDate).text=qrResult?.calendar?.toFormattedDisplay()
        dialog.findViewById<AppCompatTextView>(R.id.scannedText).text="QR Code"+qrResult!!.result
        dialog.show()
    }

    private fun onClicks() {


        dialog.findViewById<AppCompatImageView>(R.id.shareResult).setOnClickListener{
        shareResult()
        }
        dialog.findViewById<AppCompatImageView>(R.id.copyResult).setOnClickListener{
          copyResultToClipboard()
        }
        dialog.findViewById<AppCompatImageView>(R.id.cancelDialog).setOnClickListener{

         dialog.dismiss()
            onDismissListener?.onDismiss()
        }
    }

    private fun copyResultToClipboard() {
        val clipboard=context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip=ClipData.newPlainText("QrScannerResult",dialog.findViewById<AppCompatTextView>(R.id.scannedText).text)
        clipboard.text=clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()

    }

    private fun shareResult(){
        val txtIntent=Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(
            Intent.EXTRA_TEXT,
            dialog.findViewById<AppCompatTextView>(R.id.scannedText).text.toString()
        )
        context.startActivity(Intent.createChooser(txtIntent, "Share QR Result"))
    }

    fun setOnDismissListener(dismissListener: OnDismissListener) {
        this.onDismissListener = dismissListener
    }
    interface OnDismissListener {
        fun onDismiss()
    }



}