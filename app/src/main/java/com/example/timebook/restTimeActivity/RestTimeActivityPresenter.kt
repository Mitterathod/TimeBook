package com.example.timebook.restTimeActivity

import com.example.timebook.model.Shift

class RestTimeActivityPresenter(private val view: View):Presenter {
    override fun getShift(shift: Shift) {
        view.displayShifts(shift)
    }
}