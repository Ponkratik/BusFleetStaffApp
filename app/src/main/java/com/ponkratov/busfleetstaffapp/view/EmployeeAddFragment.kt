package com.ponkratov.busfleetstaffapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ponkratov.busfleetstaffapp.appDatabase
import com.ponkratov.busfleetstaffapp.databinding.FragmentEmployeeAddBinding
import com.ponkratov.busfleetstaffapp.model.Employee
import com.ponkratov.busfleetstaffapp.view.extension.validateEmptyString
import com.ponkratov.busfleetstaffapp.viewmodel.EmployeeAddViewModel
import com.ponkratov.busfleetstaffapp.viewmodel.StaffListViewModel

class EmployeeAddFragment : Fragment() {
    private var _binding: FragmentEmployeeAddBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val employeeDao by lazy {
        requireContext().appDatabase.employeeDao()
    }

    private val employeeAddViewModel: EmployeeAddViewModel by viewModels {
        viewModelFactory {
            initializer {
                EmployeeAddViewModel(employeeDao)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEmployeeAddBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonAdd.setOnClickListener {
                if (!containerFirstName.validateEmptyString(editTextFirstName.text.toString(), "First name couldn't be blank")) {
                    return@setOnClickListener
                }

                if (!containerLastName.validateEmptyString(editTextLastName.text.toString(), "Last name couldn't be blank")) {
                    return@setOnClickListener
                }

                if (!containerPosition.validateEmptyString(editTextPosition.text.toString(), "Position couldn't be blank")) {
                    return@setOnClickListener
                }

                employeeAddViewModel.onAddButtonClicked(
                    Employee(
                        0,
                        editTextFirstName.text.toString(),
                        editTextLastName.text.toString(),
                        editTextPosition.text.toString()
                    )
                )

                Toast.makeText(requireContext(), "Employee added uccessfully", Toast.LENGTH_SHORT).show()
                editTextFirstName.setText("")
                editTextLastName.setText("")
                editTextPosition.setText("")
                containerFirstName.error = ""
                containerLastName.error = ""
                containerPosition.error = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}