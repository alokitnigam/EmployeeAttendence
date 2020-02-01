package com.example.syncbyte.DI.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class AttendenceModel (
     @PrimaryKey(autoGenerate = true)
     var id:Long=0,
     var entryTime:Long,
     var exitTime:Long? = null,
     var employeeId:String
)