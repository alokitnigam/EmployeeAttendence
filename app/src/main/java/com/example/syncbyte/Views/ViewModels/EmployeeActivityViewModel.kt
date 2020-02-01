package com.example.syncbyte.Views.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.DI.database.EmployeeRepo
import javax.inject.Inject

class EmployeeActivityViewModel @Inject
constructor(val db:EmployeeRepo) : ViewModel()
{
    var employees: LiveData<List<EmployeeModel>> = MutableLiveData()
    fun checkIn(attendenceModel: AttendenceModel): Long {
       return  db.addAttendence(attendenceModel)
    }

    fun checkOut(attendenceId: Long, exittime: Long) {
        db.updateAttendence(attendenceId,exittime)
    }

    fun getAllEmployees(){
        employees= db.getEmployees()
    }
}