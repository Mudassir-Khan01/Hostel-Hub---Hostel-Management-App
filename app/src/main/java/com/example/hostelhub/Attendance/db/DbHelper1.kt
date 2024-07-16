package com.example.hostelhub.Attendance.db

import com.example.hostelhub.Attendance.db.Entity.QrResult

interface DbHelper1 {
    fun insertQRResult(result: String): Int
    fun getQRResult(id: Int): QrResult
    fun deleteQrResult(id: Int): Int

    fun getAllQRScannedResult(): List<QrResult>


    fun deleteAllQRScannedResult()

}