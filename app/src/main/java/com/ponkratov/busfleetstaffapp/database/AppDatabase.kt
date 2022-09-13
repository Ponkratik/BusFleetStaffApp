package com.ponkratov.busfleetstaffapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ponkratov.busfleetstaffapp.dao.EmployeeDao
import com.ponkratov.busfleetstaffapp.model.Employee

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}