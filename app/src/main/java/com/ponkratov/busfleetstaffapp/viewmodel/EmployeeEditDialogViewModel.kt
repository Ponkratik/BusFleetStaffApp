package com.ponkratov.busfleetstaffapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ponkratov.busfleetstaffapp.dao.EmployeeDao
import com.ponkratov.busfleetstaffapp.model.Employee

class EmployeeEditDialogViewModel(
    private val employeeDao: EmployeeDao
) : ViewModel() {

    private var currentItem: Employee? = null

    fun onEditItemClicked(item: Employee) {
        currentItem = item;
    }

    fun onSaveDialogOkButtonClicked(item: Employee) {
        employeeDao.update(
            Employee(
                requireNotNull(currentItem).id,
                item.firstName,
                item.lastName,
                item.position
            )
        )
    }

    fun onSaveDialogCancelButtonClicked() {
        currentItem = null
    }
}