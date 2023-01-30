package com.msk.simpletodo.presentation.view.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskEditBinding
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.util.dateTimeTrim
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import java.text.DecimalFormat
import java.util.*


class TaskEditFragment : Fragment() {

    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    private val cal by lazy { Calendar.getInstance() }
    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        val todoEntity: TaskMainFragmentArgs by navArgs()

        with(binding) {
            taskTitle.requestFocus()
            (activity as TodoActivity).hideBottomNavigation()

            taskTitle.setText(todoEntity.item.title, TextView.BufferType.EDITABLE)
            taskDescription.setText(todoEntity.item.description, TextView.BufferType.EDITABLE)
            taskDateSelect.text = todoEntity.item.date
            taskTimeSelect.text = todoEntity.item.time

            taskDateSelect.setOnClickListener {
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val format = DecimalFormat("00")
                        val padMonth = format.format(month + 1)
                        val padDay = format.format(dayOfMonth)
                        binding.taskDateSelect.text = "$year/$padMonth/$padDay"
                    }
                DatePickerDialog(
                    requireContext(),
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            taskTimeSelect.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        val format = DecimalFormat("00")
                        val padTime = format.format(hourOfDay)
                        val padMinute = format.format(minute)
                        taskTimeSelect.text = "$padTime:$padMinute"
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                ).show()
            }

            val categoryList = resources.getStringArray(R.array.category_array)
            val categoryAdapter = ArrayAdapter(
                requireContext(),
                R.layout.todo_category_item,
                categoryList
            )
            taskCategorySpinner.adapter = categoryAdapter
            taskCategorySpinner.setSelection(categoryList.indexOf(todoEntity.item.category))

            taskCreateButton.setOnClickListener {
                val title = taskTitle.text.toString()
                val description = taskDescription.text.toString()
                val date = taskDateSelect.text.toString().dateTimeTrim()
                val time = taskTimeSelect.text.toString().dateTimeTrim()
                val categoryString = when (taskCategorySpinner.selectedItemPosition) {
                    0 -> "Personal"
                    1 -> "Work"
                    2 -> "Study"
                    3 -> "Others"
                    else -> throw IllegalArgumentException("Can't have category")
                }
                val editTodoEntity = todoEntity.item.copy(
                    title = title,
                    description = description,
                    date = date,
                    time = time,
                    category = categoryString
                )
                todoViewModel.editTodo(editTodoEntity)
                this@TaskEditFragment.findNavController()
                    .navigate(R.id.action_taskEditFragment_to_taskMainFragment)
                popUpAction.emptySnackBar(it, "Edit Complete!")
            }

            backButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}