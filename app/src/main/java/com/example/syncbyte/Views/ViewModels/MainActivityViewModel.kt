package com.example.syncbyte.Views.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.DI.database.EmployeeRepo
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(val db:EmployeeRepo) : ViewModel()
{
    var check:MutableLiveData<Boolean> = MutableLiveData()
    fun saveEmployee(employeeModel: EmployeeModel){
        db.addEmployee(employeeModel)


    }

    fun checkEmployee(id:String){
        val list = db.checkEmployee(id)
        if(list.isEmpty()){
            return check.postValue(true)
        }else{
            return check.postValue(false)

        }
    }

    fun updateEmployee(employeeModel: EmployeeModel){
        db.updateEmployee(employeeModel)
    }

    fun deleteEmployee(employeeModel: EmployeeModel) {
        db.deleteEmployee(employeeModel)

    }

}