package com.example.hostelhub.Attendance.db

import com.example.hostelhub.Attendance.db.Entity.QrResult
import com.example.hostelhub.Attendance.db.database.QrResultDatabase
import java.util.Calendar

class DbHelper(var qeResultDatabase:QrResultDatabase):DbHelper1 {
    override fun insertQRResult(result: String): Int {
        val time=Calendar.getInstance()
        val resultType=findResultType(result)
        val qrResult=QrResult(result=result, resultType = resultType, calendar = time)
        return qeResultDatabase.getQrDao().insertQrResult(qrResult).toInt()
    }


    override fun getQRResult(id: Int): QrResult {
        return qeResultDatabase.getQrDao().getQrResult(id)
    }


    override fun deleteQrResult(id: Int): Int {
        return qeResultDatabase.getQrDao().deleteQrResult(id)
    }

    override fun getAllQRScannedResult(): List<QrResult> {
        return qeResultDatabase.getQrDao().getAllScannedResult()
    }



    override fun deleteAllQRScannedResult() {
        qeResultDatabase.getQrDao().deleteAllScannedResult()
    }


    private fun findResultType(result: String): String {
        return "TEXT"
    }

}