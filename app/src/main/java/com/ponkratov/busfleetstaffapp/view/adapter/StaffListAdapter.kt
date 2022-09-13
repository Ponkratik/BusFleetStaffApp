package com.ponkratov.busfleetstaffapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.busfleetstaffapp.databinding.ItemEmployeeBinding
import com.ponkratov.busfleetstaffapp.model.Employee

class StaffListAdapter(
    context: Context,
    private val onItemClicked: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeViewHolder>(DIFF_UTIL) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            binding = ItemEmployeeBinding.inflate(layoutInflater, parent, false),
            onItemClicked = onItemClicked
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
                return oldItem.firstName == newItem.firstName
                        && oldItem.lastName == newItem.lastName
                        && oldItem.position == newItem.position
            }
        }
    }
}

class EmployeeViewHolder(
    private val binding: ItemEmployeeBinding,
    private val onItemClicked: (Employee) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Employee) {
        with(binding) {
            employeeFirstName.text = item.firstName
            employeeLastName.text = item.lastName
            employeePosition.text = item.position

            root.setOnClickListener { onItemClicked(item) }
        }
    }
}

