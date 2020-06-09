package com.example.timebook

import android.widget.EditText

fun verifyView(vararg editTexts: EditText): Boolean {
    for (editText in editTexts) {
        if (editText.isFilled()) {
            editText.error = editText.context.getString(R.string.field_required)
            return false
        }
    }
    return true
}


fun EditText.isFilled(): Boolean {
    if (text.toString().trim() == "") {
        return true
    }
    return false
}