package com.example.syncbyte.DI.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel

@Database(entities = [EmployeeModel::class,AttendenceModel::class], version = 1,exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun audioDao(): EmployeeRepo

    companion object {
        val DATABASE_NAME = "employee.db"
    }


}
