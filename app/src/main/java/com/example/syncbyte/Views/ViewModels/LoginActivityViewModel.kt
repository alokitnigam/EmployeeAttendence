package com.example.syncbyte.Views.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.DI.database.EmployeeRepo
import javax.inject.Inject

class LoginActivityViewModel @Inject
constructor(val db:EmployeeRepo) : ViewModel()
{
    var check: MutableLiveData<EmployeeModel> = MutableLiveData()

    fun loginEmployee(id:String,password:String){
       val count= db.loginEmployee(id,password)
        if(!count .isEmpty()){
            check.postValue(count[0])
        }else{
            check.postValue(null)

        }
    }

}