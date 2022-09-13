package com.ponkratov.busfleetstaffapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ponkratov.busfleetstaffapp.appDatabase
import com.ponkratov.busfleetstaffapp.databinding.FragmentStaffListBinding
import com.ponkratov.busfleetstaffapp.view.adapter.StaffListAdapter
import com.ponkratov.busfleetstaffapp.view.extension.addVerticalSpace
import com.ponkratov.busfleetstaffapp.viewmodel.EmployeeEditDialogViewModel
import com.ponkratov.busfleetstaffapp.viewmodel.StaffListViewModel

class StaffListFragment : Fragment() {
    private var _binding: FragmentStaffListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val employeeDao by lazy {
        requireContext().appDatabase.employeeDao()
    }

    private val staffListViewModel: StaffListViewModel by viewModels {
        viewModelFactory {
            initializer {
                StaffListViewModel(employeeDao)
            }
        }
    }

    private val employeeEditDialogViewModel: EmployeeEditDialogViewModel by viewModels {
        viewModelFactory {
            initializer {
                EmployeeEditDialogViewModel(employeeDao)
            }
        }
    }

    private val adapter by lazy {
        StaffListAdapter(
            context = requireContext(),
            onItemClicked = {
                EmployeeEditDialog(it).show(childFragmentManager, "employee_edit_dialog")
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentStaffListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            staffRecyclerView.adapter = adapter
            staffRecyclerView.addVerticalSpace()

            staffListViewModel.liveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isBlank()) {
                        staffListViewModel.onSearchQueryChanged("")
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    staffListViewModel.onSearchQueryChanged(query)
                    return true
                }

            })

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val deletedItem = adapter.currentList[position]
                    adapter.notifyItemRemoved(position)
                    staffListViewModel.onItemSwiped(deletedItem)

                    Snackbar.make(
                        requireView(),
                        "Deleted ${deletedItem.firstName} ${deletedItem.lastName}",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Undo") {
                            adapter.notifyItemInserted(position)
                            staffListViewModel.onUndoSnackbarClicked()
                        }.show()
                }
            }).attachToRecyclerView(staffRecyclerView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}