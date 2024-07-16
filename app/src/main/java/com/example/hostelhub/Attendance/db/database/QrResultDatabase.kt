package com.example.hostelhub.Attendance.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hostelhub.Attendance.db.Entity.QrResult
import com.example.hostelhub.Attendance.db.dao.QrResultDao


@Database(entities = [QrResult::class], version = 1, exportSchema = false)
abstract  class QrResultDatabase:RoomDatabase() {
    abstract fun getQrDao():QrResultDao

    companion object{
        private const val DB_NAME="QrResultDatabase"
        private var qrResultDataBase:QrResultDatabase?=null
        fun getAppDatabase(context:Context):QrResultDatabase?{
            if(qrResultDataBase==null){
                qrResultDataBase=Room.databaseBuilder(context.applicationContext,QrResultDatabase::class.java,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return qrResultDataBase

        }
        fun destroyInstance() {
            qrResultDataBase = null
        }
    }
}