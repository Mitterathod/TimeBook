package com.example.timebook.restTimeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.timebook.R
import com.example.timebook.toolsActivity.ToolsActivity
import com.example.timebook.mainActivity.MainActivity
import com.example.timebook.model.Shift
import kotlinx.android.synthetic.main.activity_rest_time.*
import kotlinx.android.synthetic.main.input_layout.*

class RestTimeActivity : AppCompatActivity(), View {
    lateinit var presenter: Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RestTimeActivityPresenter(this)
        setContentView(R.layout.activity_rest_time)
        val actionBar = supportActionBar
        actionBar!!.hide()
        val shift: Shift? = intent.getParcelableExtra("shift")
        if (shift != null) {
            presenter.getShift(shift)
        }
        image_car.setOnClickListener {
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }
        image_edit.setOnClickListener {
//            val intent = Intent(this,EditActivity::class.java)
//            startActivity(intent)
        }
        image_tools_rest.setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun displayShifts(shift: Shift) {
        driversCode.setText(shift.driverCode)
        driverNameET.setText(shift.driverName)
        //employeeNameSpinner.selectedItem = shift.employeeNumber

        val employeeNr =
            mutableListOf(shift.employeeNumber)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            employeeNr
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        employeeNameSpinner.adapter = adapter
        edit_1.setText(shift.date)
        startTimeET1.setText(shift.startTime1)
        endTimeET1.setText(shift.startTime)
        startTimeET2.setText(shift.endTime)
    }
}
