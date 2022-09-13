package com.ponkratov.busfleetstaffapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ponkratov.busfleetstaffapp.dao.EmployeeDao
import com.ponkratov.busfleetstaffapp.model.Employee

class EmployeeAddViewModel(
    private val employeeDao: EmployeeDao
) : ViewModel() {

    fun onAddButtonClicked(newEmployee: Employee) {
        employeeDao.insertAll(newEmployee)
    }
}