package com.example.timebook.toolsActivity

import android.util.Log
import com.example.timebook.getDatabaseReference
import com.example.timebook.model.Driver
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class ToolsPresenter(private val view: View) : Presenter {

    private val db: FirebaseFirestore = getDatabaseReference()

    override fun verifyDetails() {
        view.verifyDetails()
    }

    override fun preFillDetails(driverCode: String) {
        view.disableUserTouchAndShowLoading()
        try {
            db.collection(COLLECTION_PATH_DRIVER).document(driverCode)
                .get().addOnCompleteListener {
                    if (it.isSuccessful && !it.result?.data.isNullOrEmpty()) {
                        val details = it.result?.data?.get(driverCode) as HashMap<*, *>
                        view.preFillDetails(details as HashMap<String, Any>)
                    }
                }
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }

        view.enableUserTouchAndDismissLoading()
    }

    override fun saveDetails(driver: Driver) {
        view.disableUserTouchAndShowLoading()
        val map = HashMap<String, Driver>()
        map[driver.driverCode] = driver
        db.collection(COLLECTION_PATH_DRIVER).document(driver.driverCode)
            .get().addOnCompleteListener {
                if (it.isSuccessful && !it.result?.data.isNullOrEmpty()) {
                    db.collection(COLLECTION_PATH_DRIVER).document(driver.driverCode)
                        .update(map as Map<String, Any>)
                        .addOnSuccessListener { view.detailsSuccessfullyUpdated() }
                        .addOnFailureListener { error -> view.errorWhileSaving(error) }
                } else {
                    db.collection(COLLECTION_PATH_DRIVER).document(driver.driverCode)
                        .set(map)
                        .addOnSuccessListener { view.detailsSuccessfullySaved() }
                        .addOnFailureListener { error -> view.errorWhileSaving(error) }
                }
            }
        view.enableUserTouchAndDismissLoading()
    }

    companion object {
        const val COLLECTION_PATH_DRIVER = "drivers"
        const val COLLECTION_PATH_SHIFT = "shifts"

    }

}