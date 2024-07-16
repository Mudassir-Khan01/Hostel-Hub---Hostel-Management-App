package com.example.hostelhub.Attendance.At_Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.hostelhub.Attendance.db.DbHelper
import com.example.hostelhub.Attendance.db.DbHelper1
import com.example.hostelhub.Attendance.db.database.QrResultDatabase
import com.example.hostelhub.Attendance.dialogs.QrCodeResultDialog
import com.example.hostelhub.R
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


class QrScannerFragment : Fragment(),ZBarScannerView.ResultHandler {
companion object{
    fun newInstance():QrScannerFragment{
        return QrScannerFragment()
    }
}
    private lateinit var mView:View
lateinit var scannerView:ZBarScannerView
lateinit var resultDialog:QrCodeResultDialog
private lateinit var dbHelper1: DbHelper1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        init()
        initViews()
        onCLicks()
        return mView.rootView
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun init(){//initialize dbhelper
        dbHelper1= DbHelper(QrResultDatabase.getAppDatabase(context!!)!!)
    }
    private fun initViews() {
        initializeQRCamera()
        setResultDialog()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setResultDialog() {
        resultDialog= QrCodeResultDialog(context!!)
        resultDialog.setOnDismissListener(object :QrCodeResultDialog.OnDismissListener{
            override fun onDismiss(){
                resetPreview()   //reset the camera
            }
        })
    }
    private fun resetPreview() {//when dialog is shown camera will freeze and when we remove it it will work normally
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }

    private fun onCLicks() {
        val flashToggle=mView.findViewById<AppCompatImageView>(R.id.flashToggle)
flashToggle.setOnClickListener{
    if(flashToggle.isSelected){
        offFlashLight()
    }else {
        onFlashLight()
    }
}
    }
    private fun onFlashLight() {
        val flashToggle=mView.findViewById<AppCompatImageView>(R.id.flashToggle)
        flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        val flashToggle=mView.findViewById<AppCompatImageView>(R.id.flashToggle)
       flashToggle.isSelected = false
        scannerView.flash = false
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initializeQRCamera() {
        val containerScanner=mView.findViewById<FrameLayout>(R.id.containerScanner)
        scannerView= ZBarScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        containerScanner.addView(scannerView)

    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }
    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
    }

    private fun onQrResult(contents: String?) {
        if(contents.isNullOrEmpty())
            showToast("Empty Qr Result")
        else
            saveToDataBase(contents)

    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }
    private fun saveToDataBase(contents: String){
        val insertedResultId=dbHelper1.insertQRResult(contents)
        val qrResult=dbHelper1.getQRResult(insertedResultId)
        resultDialog.show(qrResult)

    }


}