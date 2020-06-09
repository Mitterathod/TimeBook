package com.example.timebook.toolsActivity

import com.example.timebook.model.Driver
import java.lang.Exception

interface View {

    fun verifyDetails(): Boolean

    fun verifyDriversCode():Boolean

    fun detailsSuccessfullySaved()

    fun errorWhileSaving(e:Exception)

    fun disableUserTouchAndShowLoading()

    fun enableUserTouchAndDismissLoading()

    fun preFillDetails(driver: HashMap<String, Any>)

    fun detailsSuccessfullyUpdated()
}


interface Presenter {

    fun saveDetails(driver: Driver)

    fun verifyDetails()

    fun preFillDetails(driverCode:String)
}