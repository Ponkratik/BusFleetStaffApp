package com.ponkratov.busfleetstaffapp.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ponkratov.busfleetstaffapp.appDatabase
import com.ponkratov.busfleetstaffapp.databinding.FragmentDialogEmployeeEditBinding
import com.ponkratov.busfleetstaffapp.model.Employee
import com.ponkratov.busfleetstaffapp.view.extension.validateEmptyString
import com.ponkratov.busfleetstaffapp.viewmodel.EmployeeEditDialogViewModel
import com.ponkratov.busfleetstaffapp.viewmodel.StaffListViewModel

class EmployeeEditDialog(private val employee: Employee) : DialogFragment() {
    private var _binding: FragmentDialogEmployeeEditBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val employeeDao by lazy {
        requireContext().appDatabase.employeeDao()
    }

    private val viewModel: EmployeeEditDialogViewModel by viewModels {
        viewModelFactory {
            initializer {
                EmployeeEditDialogViewModel(employeeDao)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDialogEmployeeEditBinding.inflate(inflater, container, false)
            .also {
                _binding = it
                viewModel.onEditItemClicked(employee)
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        with(binding) {
            editTextFirstName.setText(employee.firstName)
            editTextLastName.setText(employee.lastName)
            editTextPosition.setText(employee.position)

            buttonSave.setOnClickListener {
                if (!containerFirstName.validateEmptyString(
                        editTextFirstName.text.toString(),
                        "First name couldn't be blank"
                    )
                ) {
                    return@setOnClickListener
                }

                if (!containerLastName.validateEmptyString(
                        editTextLastName.text.toString(),
                        "Last name couldn't be blank"
                    )
                ) {
                    return@setOnClickListener
                }

                if (!containerPosition.validateEmptyString(
                        editTextPosition.text.toString(),
                        "Position couldn't be blank"
                    )
                ) {
                    return@setOnClickListener
                }

                viewModel.onSaveDialogOkButtonClicked(
                    Employee(
                        0,
                        editTextFirstName.text.toString(),
                        editTextLastName.text.toString(),
                        editTextPosition.text.toString()
                    )
                )

                Toast.makeText(requireContext(), "Employee updated uccessfully", Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            }

            buttonCancel.setOnClickListener {
                viewModel.onSaveDialogCancelButtonClicked()
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}