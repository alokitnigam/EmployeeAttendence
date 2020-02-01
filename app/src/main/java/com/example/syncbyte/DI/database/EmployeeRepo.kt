package com.example.syncbyte.DI.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel

@Dao
interface EmployeeRepo{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAttendence(attendenceModel: AttendenceModel):Long

    @Query("UPDATE attendencemodel Set exitTime = :exititme where id =:attendenceid ")
    fun updateAttendence(attendenceid:Long,exititme:Long)

    @Delete
    fun deletAttendence(attendenceModel: AttendenceModel)

    @Update
    fun updateEmployee(employeeModel: EmployeeModel)

    @Query("Select * from EmployeeModel")
    fun getEmployees() : LiveData<List<EmployeeModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEmployee(employeeModel: EmployeeModel)

    @Query("SELECT * from employeemodel WHERE employeeId= :id")
    fun checkEmployee(id: String):List<EmployeeModel>

    @Query("SELECT * from employeemodel WHERE employeeId= :id and password =:password")
    fun loginEmployee(id: String, password: String) :List<EmployeeModel>

    @Query("SELECT * from attendencemodel WHERE employeeId= :id ")
    fun getAttendenceByEmployeeId(id: String):LiveData<List<AttendenceModel>>

    @Delete
    fun deleteEmployee(employeeModel: EmployeeModel)


}
