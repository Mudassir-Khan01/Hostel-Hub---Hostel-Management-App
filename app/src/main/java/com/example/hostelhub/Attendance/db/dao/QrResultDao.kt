package com.example.hostelhub.Attendance.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hostelhub.Attendance.db.Entity.QrResult

@Dao
interface QrResultDao {
    @Query("SELECT * FROM QrResult ORDER BY time DESC")
    fun getAllScannedResult(): List<QrResult>

    @Query("DELETE FROM QrResult")
    fun deleteAllScannedResult()

    @Query("DELETE FROM QrResult WHERE id = :id")
    fun deleteQrResult(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQrResult(qrResult: QrResult): Long

    @Query("SELECT * FROM QrResult WHERE id = :id")
    fun getQrResult(id: Int): QrResult

    @Query("SELECT * FROM QrResult WHERE result = :result ")
    fun checkIfQrResultExist(result: String): Int
}