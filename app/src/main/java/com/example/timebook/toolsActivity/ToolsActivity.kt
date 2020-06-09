package com.example.timebook.toolsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.timebook.R
import com.example.timebook.mainActivity.MainActivity
import com.example.timebook.model.Driver
import com.example.timebook.model.Employee
import com.example.timebook.verifyView
import kotlinx.android.synthetic.main.activity_rest_time.image_car
import kotlinx.android.synthetic.main.activity_tools.*
import java.lang.Exception

class ToolsActivity : AppCompatActivity(), View {

    lateinit var presenter: ToolsPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)
        presenter = ToolsPresenter(this)
        val actionBar = supportActionBar
        actionBar!!.hide()
        image_car.setOnClickListener {
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }
//        image_download.setOnClickListener {
//            val intent = Intent(this,DownloadActivity::class.java)
//            startActivity(intent)
//        }
        save_driver_info.setOnClickListener {
            //Create a list of employers
            val employee = mutableListOf(
                Employee(
                    employerNameET.text.trim().toString(),
                    employerAddress.text.trim().toString()
                )
            )
            //Adds all other Employers to the list if provided
            createAllEmployers(employee)
            val driver = Driver(
                name = driverNameET.text.trim().toString(),
                driverCode = employeeNameET.text.trim().toString(),
                address = driverNameET1.text.trim().toString(),
                phoneNumber = restTimeET.text.trim().toString(),
                employee = employee
            )
            if (verifyDetails() && verifyDriversCode()) {
                presenter.saveDetails(driver)
            } else {
                return@setOnClickListener
            }

        }
        employeeNameET.addTextChangedListener(afterTextChanged = {
            if (it?.length == 6) {
                presenter.preFillDetails(it.toString())
            }
        })
    }

    private fun createAllEmployers(employee: MutableList<Employee>) {
        //checks if Details of the second Employer is provided
        //if provided it adds it to the list of employers
        if (employerNameET1.text.isNotEmpty()) {
            employee.add(
                Employee(
                    employerNameET1.text.trim().toString(),
                    employerAddress1.text.trim().toString()
                )
            )
        }
        //checks if Details of the Third Employer is provided
        //if provided it adds it to the list of employers
        if (employerNameET2.text.isNotEmpty()) {
            employee.add(
                Employee(
                    employerNameET2.text.trim().toString(),
                    employerAddress2.text.trim().toString()
                )
            )
        }
    }

    override fun verifyDetails(): Boolean {
        return verifyView(
            employeeNameET,
            driverNameET,
            driverNameET1,
            restTimeET,
            edit_1,
            employerAddress,
            employerNameET
        )
    }

    override fun verifyDriversCode(): Boolean {
        if (employeeNameET.text.length != 6) {
            Toast.makeText(this, "Unable To Save", Toast.LENGTH_LONG).show()
            employeeNameET.error = "Drivers Code Must be  Six Digits "
            return false
        }
        return true
    }


    override fun detailsSuccessfullySaved() {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_LONG).show()
    }

    override fun errorWhileSaving(e: Exception) {
        Toast.makeText(this, "Failed with an Error $e", Toast.LENGTH_LONG).show()
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

    override fun preFillDetails(driver: HashMap<String, Any>) {
        driverNameET.setText(driver["name"].toString())
        driverNameET1.setText(driver["address"].toString())
        restTimeET.setText(driver["phoneNumber"].toString())
        val employee = driver["employee"] as ArrayList<*>
        loadEmployee(employee as ArrayList<HashMap<String, String>>)
    }


    //Loads the List of Employee Saved Formerly By a Driver maximum of 3 employees
    private fun loadEmployee(employee: ArrayList<HashMap<String, String>>) {
        employerNameET.setText(employee[0]["name"])
        employerAddress.setText(employee[0]["address"])
        employerNameET1.setText(employee.elementAtOrNull(1)?.get("name"))
        employerAddress1.setText(employee.elementAtOrNull(1)?.get("address"))
        employerNameET2.setText(employee.elementAtOrNull(2)?.get("name"))
        employerAddress2.setText(employee.elementAtOrNull(2)?.get("address"))
    }


    override fun detailsSuccessfullyUpdated() {
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
    }
}

