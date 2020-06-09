package com.example.timebook.model

import android.os.Parcel
import android.os.Parcelable

data class Shift(
    val driverCode: String,
    val driverName: String,
    val employeeNumber: String,
    val date: String,
    val startTime1: String,
    val startTime: String,
    val endTime: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(driverCode)
        parcel.writeString(driverName)
        parcel.writeString(employeeNumber)
        parcel.writeString(date)
        parcel.writeString(startTime1)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Shift> {
        override fun createFromParcel(parcel: Parcel): Shift {
            return Shift(parcel)
        }

        override fun newArray(size: Int): Array<Shift?> {
            return arrayOfNulls(size)
        }
    }
}