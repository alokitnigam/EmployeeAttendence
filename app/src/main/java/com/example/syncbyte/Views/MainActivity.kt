package com.example.syncbyte.Views

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.syncbyte.Base.BaseActivity
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.R
import com.example.syncbyte.Views.ViewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainActivityViewModel>() {
    val myCalendar = Calendar.getInstance()

    lateinit var employeeModel :EmployeeModel
    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delete.visibility = View.INVISIBLE

        if(intent.hasExtra("employee")){
            employeeModel = intent.getSerializableExtra("employee") as EmployeeModel

            name.setText(employeeModel.name)
            dob.setText(employeeModel.dob)
            emailid.setText(employeeModel.email)
            password.setText(employeeModel.password)
            phone.setText(employeeModel.phone)
            employeeid.setText(employeeModel.employeeId)
            login.visibility = View.GONE
            employeeid.isFocusable =false
            delete.visibility = View.VISIBLE


        }
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {

        save.setOnClickListener {

            if(intent.hasExtra("employee")){

                  updateEmployee()
            }else{
                if(!employeeid.text.isNullOrEmpty()){
                    getViewModel().checkEmployee(employeeid.text.toString())
                }else{
                    Toast.makeText(this,"Enter an Employee Id first",Toast.LENGTH_SHORT).show()

                }
            }


        }

        login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))

        }

        delete.setOnClickListener {
            getViewModel().deleteEmployee(employeeModel)
            finish()
        }

        dob.isFocusable =false
        dob.setOnClickListener {
            DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun setUpObservers() {
        getViewModel().check.observe(this, Observer {
            if (!it){
                Toast.makeText(this,"Employee Id already Present",Toast.LENGTH_SHORT).show()
            }else{
                continueLogin()
            }
        })


    }

    private fun continueLogin() {
        if(name.text.isNullOrBlank() || password.text.isNullOrBlank() || dob.text.isNullOrBlank() ||emailid.text.isNullOrBlank()
            || password.text.isNullOrBlank()){
            Toast.makeText(this,"Enter all Fields",Toast.LENGTH_SHORT).show()

        }else{
            val employeeModel =EmployeeModel(name = name.text.toString(),email =emailid.text.toString(),dob = dob.text.toString()
                ,employeeId = employeeid.text.toString(),phone = phone.text.toString(),password = password.text.toString())
            this.employeeModel = employeeModel

            getViewModel().saveEmployee(employeeModel)
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun updateEmployee(){
        if(name.text.isNullOrBlank() || password.text.isNullOrBlank() || dob.text.isNullOrBlank() ||emailid.text.isNullOrBlank()
            || password.text.isNullOrBlank()){
            Toast.makeText(this,"Enter all Fields",Toast.LENGTH_SHORT).show()

        }else{
            val employeeModel =EmployeeModel(name = name.text.toString(),email =emailid.text.toString(),dob = dob.text.toString()
                ,employeeId = employeeid.text.toString(),phone = phone.text.toString(),password = password.text.toString())
            this.employeeModel = employeeModel

            getViewModel().updateEmployee(employeeModel)
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainActivityViewModel {
        return mainActivityViewModel
    }

    var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dob.setText(sdf.format(myCalendar.time))
    }

}
