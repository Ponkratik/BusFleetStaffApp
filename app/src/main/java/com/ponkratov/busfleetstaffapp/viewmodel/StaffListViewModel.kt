package com.ponkratov.busfleetstaffapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ponkratov.busfleetstaffapp.dao.EmployeeDao
import com.ponkratov.busfleetstaffapp.model.Employee

class StaffListViewModel(
    private val employeeDao: EmployeeDao
) : ViewModel() {

    private val _liveData = MutableLiveData(employeeDao.getAllByName(""))
    val liveData: LiveData<List<Employee>> = _liveData

    private var currentItem: Employee? = null

    fun onSearchQueryChanged(query: String) {
        _liveData.value = employeeDao.getAllByName(query)
    }

    fun onItemSwiped(item: Employee) {
        employeeDao.delete(item)
        currentItem = item
    }

    fun onUndoSnackbarClicked() {
        employeeDao.insertAll(requireNotNull(currentItem))
        currentItem = null
    }
}