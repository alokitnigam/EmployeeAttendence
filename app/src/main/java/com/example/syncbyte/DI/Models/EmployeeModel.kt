package com.example.syncbyte.DI.Models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class EmployeeModel(
    @PrimaryKey
    var employeeId : String,
    val name: String?,
    val phone: String?,
    var email:String?,
    var dob:String?,
    var password:String?

):Serializable