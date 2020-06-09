package com.example.timebook.restTimeActivity

import com.example.timebook.model.Shift

interface View {

    fun displayShifts(shift: Shift)
}

interface Presenter {
    fun getShift(shift: Shift)
}