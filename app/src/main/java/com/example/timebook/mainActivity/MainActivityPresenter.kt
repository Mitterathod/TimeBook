package com.example.timebook.mainActivity

import android.util.Log
import com.example.timebook.*
import com.example.timebook.model.Shift
import com.example.timebook.toolsActivity.ToolsPresenter
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class MainActivityPresenter(private val view: View) : Presenter {

    private val db: FirebaseFirestore = getDatabaseReference()

    override fun getStartTime() {
        view.setStartTimes(getPresentTime())
    }

    override fun getEndTime() {
        view.setEndTime(getPresentTime())
    }

    override fun setDate() {
        view.displayDate(getDate())
    }

    override fun getDriverNameAndEmployeeList(driverCode: String) {
        view.disableUserTouchAndShowLoading()
        try {
            db.collection(ToolsPresenter.COLLECTION_PATH_DRIVER).document(driverCode)
                .get().addOnCompleteListener {
                    if (it.isSuccessful && !it.result?.data.isNullOrEmpty()) {
                        val details = it.result?.data?.get(driverCode) as HashMap<*, *>
                        view.prefillNameAndEmployeeList(details as HashMap<String, Any>)
                    }
                }
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }

        view.enableUserTouchAndDismissLoading()
    }

    override fun saveNewLog(shift: Shift) {
        view.disableUserTouchAndShowLoading()
        db.collection(ToolsPresenter.COLLECTION_PATH_SHIFT).document(shift.driverCode)
            .set(shift)
            .addOnSuccessListener { view.detailsSuccessfullySaved() }
            .addOnFailureListener { view.errorWhileSaving(it) }
        view.enableUserTouchAndDismissLoading()
    }

}