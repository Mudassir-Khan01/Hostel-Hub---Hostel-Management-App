package com.example.hostelhub.Attendance.At_Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hostelhub.Attendance.Adapter.ScannedResultListAdapter
import com.example.hostelhub.Attendance.db.DbHelper
import com.example.hostelhub.Attendance.db.DbHelper1
import com.example.hostelhub.Attendance.db.Entity.QrResult
import com.example.hostelhub.Attendance.db.database.QrResultDatabase
import com.example.hostelhub.Attendance.utils.gone
import com.example.hostelhub.Attendance.utils.visible
import com.example.hostelhub.R


class ScannedHistoryFragment : Fragment() {
enum class ResultListType{
    All_RESULT
}
    companion object{
        private const val ARGUMENT_RESULT_LIST_TYPE="ArgumentResultType"
        fun newInstance(screenType: ResultListType):ScannedHistoryFragment{
            val bundle=Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE,screenType)
            val fragment=ScannedHistoryFragment()
            fragment.arguments=bundle
            return fragment
    }
    }
private   var resultType:ResultListType?=null
private     lateinit var mView: View
private lateinit var dbHelper1: DbHelper1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    private fun handleArguments() {
        resultType=arguments?.getSerializable(ARGUMENT_RESULT_LIST_TYPE)as ResultListType
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView= inflater.inflate(R.layout.fragment_scanned_history, container, false)
        init()
        setSwipeRefreshLayout()
        onClicks()
        showListOfResults()
        return mView.rootView
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun init(){
        dbHelper1= DbHelper(QrResultDatabase.getAppDatabase(context!!)!!)
        mView.findViewById<View>(R.id.layoutHeader).findViewById<AppCompatTextView>(R.id.tvHeaderText).text=getString(R.string.recent_scanned_results)

    }

    @SuppressLint("CutPasteId")
    private fun setSwipeRefreshLayout() {
       val swipeRefresh= mView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
           swipeRefresh.isRefreshing=false
            showListOfResults()
        }
    }

    private fun showListOfResults(){
        when(resultType){
            ResultListType.All_RESULT -> showAllResults()
            else -> {}
        }

    }

    private fun showAllResults() {
        var listOfAllResult=dbHelper1.getAllQRScannedResult()
        showResults(listOfAllResult)
        val layoutHeader=mView.findViewById<View>(R.id.layoutHeader)
layoutHeader.findViewById<AppCompatTextView>(R.id.tvHeaderText).text=getString(R.string.recent_scanned)
    }

    private fun showResults(listOfQrResult: List<QrResult>) {
        if(listOfQrResult.isNotEmpty()){
            initRecyclerView(listOfQrResult)

        }else{
            showEmptyState()

        }
    }

    private fun showEmptyState() {
       val scannedHistoryRecyclerView=mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)!!
        scannedHistoryRecyclerView.findViewById<AppCompatImageView>(R.id.removeAll)?.gone()
        scannedHistoryRecyclerView.gone()

        val noResultFound=mView.findViewById<AppCompatImageView>(R.id.noResultFound)!!
        noResultFound.visible()
    }

    @SuppressLint("UseRequireInsteadOfGet", "CutPasteId")
    private fun initRecyclerView(listOfQrResult: List<QrResult>) {
        val scannedHistoryRecyclerView=mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)!!

        scannedHistoryRecyclerView.layoutManager=LinearLayoutManager(context)
        scannedHistoryRecyclerView.adapter=ScannedResultListAdapter(dbHelper1,context!!,listOfQrResult.toMutableList())
        showRecyclerView()

    }
    private fun showRecyclerView() {
        mView.findViewById<View>(R.id.layoutHeader).findViewById<AppCompatImageView>(R.id.removeAll).visible()
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView).visible()
        mView.findViewById<AppCompatImageView>(R.id.noResultFound).gone()
    }

    private fun onClicks() {
        mView.findViewById<View>(R.id.layoutHeader).findViewById<AppCompatImageView>(R.id.removeAll).setOnClickListener {
            showRemoveAllScannedResultDialog()
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun showRemoveAllScannedResultDialog() {
        AlertDialog.Builder(context!!,R.style.CustomAlertDialog).setTitle(getString(R.string.clear_all))
            .setMessage(getString(R.string.clear_all_result))
            .setPositiveButton(getString(R.string.clear)) { _, _ ->
                clearAllRecords()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    private fun clearAllRecords() {

        when(resultType){
            ResultListType.All_RESULT->dbHelper1.deleteAllQRScannedResult()
            else -> {}
        }
        val scannedHistoryRecyclerView=mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)
        scannedHistoryRecyclerView.adapter?.notifyDataSetChanged()
        showListOfResults()
    }


}