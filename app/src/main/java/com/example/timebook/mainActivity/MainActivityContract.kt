package com.example.timebook.mainActivity

import com.example.timebook.model.Shift
import java.lang.Exception

interface View {

    fun setStartTimes(time: String)

    fun setEndTime(time: String)

    fun displayDate(date: String)

    fun prefillNameAndEmployeeList(driver: HashMap<String, Any>)

    fun disableUserTouchAndShowLoading()

    fun enableUserTouchAndDismissLoading()

    fun displayShift(shift: Shift)

    fun detailsSuccessfullySaved()

    fun errorWhileSaving(e: Exception)
}


interface Presenter {

    fun getStartTime()

    fun getEndTime()

    fun setDate()

    fun getDriverNameAndEmployeeList(driverCode: String)

    fun saveNewLog(shift: Shift)
}