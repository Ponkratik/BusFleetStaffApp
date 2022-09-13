package com.ponkratov.busfleetstaffapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ponkratov.busfleetstaffapp.model.Employee

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee WHERE first_name || ' ' || last_name LIKE '%' || :query || '%'")
    fun getAllByName(query: String): List<Employee>

    @Update
    fun update(employee: Employee)

    @Insert
    fun insertAll(vararg employees: Employee)

    @Delete
    fun delete(employee: Employee)
}