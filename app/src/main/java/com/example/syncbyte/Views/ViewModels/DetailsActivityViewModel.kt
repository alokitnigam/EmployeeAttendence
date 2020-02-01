package com.example.syncbyte.Views.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.DI.database.EmployeeRepo
import javax.inject.Inject

class DetailsActivityViewModel @Inject
constructor(val db:EmployeeRepo) : ViewModel()
{
    var attendencedata: LiveData<List<AttendenceModel>> = MutableLiveData()
    var check:MutableLiveData<Boolean> = MutableLiveData()
    fun deleteAttendence(attendenceModel: AttendenceModel){
        db.deletAttendence(attendenceModel)


    }

    fun getAllAttendence(id:String){
        attendencedata = db.getAttendenceByEmployeeId(id)

    }

}