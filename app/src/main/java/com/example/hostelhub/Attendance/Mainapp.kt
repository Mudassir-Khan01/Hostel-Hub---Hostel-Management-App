package com.example.hostelhub.Attendance

import android.app.Application
import com.facebook.stetho.Stetho

class Mainapp :Application(){
    override fun onCreate() {
        super.onCreate()
Stetho.initializeWithDefaults(this)
    }
}