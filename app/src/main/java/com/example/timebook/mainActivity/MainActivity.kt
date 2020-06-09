package com.example.timebook.mainActivity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.timebook.R
import com.example.timebook.model.Shift
import com.example.timebook.restTimeActivity.RestTimeActivity
import com.example.timebook.toolsActivity.ToolsActivity
import com.example.timebook.verifyView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_layout.*
import java.lang.Exception


class MainActivity : AppCompatActivity(), View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val presenter = MainActivityPresenter(this)

        presenter.setDate()

        driversCode.addTextChangedListener(afterTextChanged = {
            if (it?.length == 6) {
                presenter.getDriverNameAndEmployeeList(it.toString())
            }
        })


        btn_start.setOnClickListener {
            progressbar.isIndeterminate = true
            presenter.getStartTime()
        }
        btn_finish.setOnClickListener {
            // Makes the ProgressBar determinate
            progressbar.isIndeterminate = false
            progressbar.max = 100
            progressbar.progress = 100
            //Set the End time when the Stop Button is pressed
            presenter.getEndTime()

            if (verifyView(driversCode, driverNameET)) {
                presenter.saveNewLog(
                    Shift(
                        driversCode.text.toString(),
                        driverNameET.text.toString(),
                        employeeNameSpinner.selectedItem.toString(),
                        edit_1.text.toString(),
                        startTimeET1.text.toString(),
                        endTimeET1.text.toString(),
                        startTimeET2.text.toString()
                    )
                )
            } else return@setOnClickListener
        }
        image_clock.setOnClickListener {
            val intent = Intent(this, RestTimeActivity::class.java)
            startActivity(intent)
        }
        image_break.setOnClickListener {
//            val intent = Intent(this,BreakActivity::class.java)
//            startActivity(intent)
        }
        image_tools.setOnClickListener {
            val intent = Intent(this, ToolsActivity::class.java)
            startActivity(intent)
        }
    }


    override fun setStartTimes(time: String) {
        if (endTimeET1.text.isNotEmpty()) {
            startTimeET1.text = endTimeET1.text
        } else {
            startTimeET1.setText(getString(R.string.zero_time))
        }
        endTimeET1.setText(time)
    }

    override fun setEndTime(time: String) {
        startTimeET2.setText(time)
    }


    override fun displayDate(date: String) {
        edit_1.setText(date)
    }

    override fun prefillNameAndEmployeeList(driver: HashMap<String, Any>) {
        driverNameET.setText(driver["name"].toString())
        val employee = driver["employee"] as ArrayList<*>

        val employeeNr =
            mutableListOf(getString(R.string.arbetsgivare_nr), "1", "2", "3")

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            employeeNr.subList(
                0,
                loadEmployee(employee as ArrayList<HashMap<String, String>>).filterNotNull().size
            )
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        employeeNameSpinner.adapter = adapter
    }


    //Loads the List of Employee Saved Name Formerly By a Driver maximum of 3 employees
    private fun loadEmployee(employee: ArrayList<HashMap<String, String>>): ArrayList<String?> {
        return arrayListOf(
            getString(R.string.arbetsgivare_nr),
            employee[0]["name"],
            employee.elementAtOrNull(1)?.get("name"),
            employee.elementAtOrNull(2)?.get("name")
        )
    }


    override fun disableUserTouchAndShowLoading() {
        progressBar.visibility = android.view.View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    override fun enableUserTouchAndDismissLoading() {
        progressBar.visibility = android.view.View.INVISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun displayShift(shift: Shift) {
        val intent = Intent(this, RestTimeActivity::class.java)
        intent.putExtra("shift", shift)
        startActivity(intent)
    }

    override fun detailsSuccessfullySaved() {
        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
    }

    override fun errorWhileSaving(e: Exception) {
        Toast.makeText(this, "Failed with an Error $e", Toast.LENGTH_LONG).show()
    }
}
