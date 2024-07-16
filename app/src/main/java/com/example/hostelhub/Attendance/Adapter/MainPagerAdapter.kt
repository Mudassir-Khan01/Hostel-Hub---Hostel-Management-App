package com.example.hostelhub.Attendance.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hostelhub.Attendance.At_Fragment.QrScannerFragment
import com.example.hostelhub.Attendance.At_Fragment.ScannedHistoryFragment

class MainPagerAdapter(var fm:FragmentManager) :FragmentPagerAdapter(fm){
    override fun getCount(): Int {
return 2;
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{QrScannerFragment.newInstance()}
            1->{ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.All_RESULT)}
            else->{QrScannerFragment()}
        }
    }

}