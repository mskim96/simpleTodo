package com.msk.simpletodo.presentation.view.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTaskCreateBinding
import com.msk.simpletodo.presentation.util.KeyboardAction
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.util.dateTimeTrim
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@AndroidEntryPoint
class TaskCreateFragment : Fragment() {

    private var _binding: FragmentTaskCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var keyBoardAction: KeyboardAction
    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val cal by lazy { Calendar.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCreateBinding.inflate(inflater, container, false)

        // For Show Keyboard
        keyBoardAction = KeyboardAction(requireActivity(), binding.taskTitle)
        lifecycleScope.launchWhenStarted {
            delay(100)
            keyBoardAction.showKeyboard()
        }

        with(binding) {
            // Focus title and hide bottom navigation
            taskTitle.requestFocus()
            (activity as TodoActivity).hideBottomNavigation()

            val sdf = SimpleDateFormat("yyyy / MM / dd", Locale("en", "ja"))
            val dtf = DateTimeFormatter.ofPattern("HH : mm", Locale("en", "ja"))
            taskDateSelect.text = sdf.format(cal.time).toString()
            taskTimeSelect.text = LocalDateTime.now().format(dtf)
            taskDateSelect.setOnClickListener {
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val format = DecimalFormat("00")
                        val padMonth = format.format(month + 1)
                        val padDay = format.format(dayOfMonth)
                        binding.taskDateSelect.text = "$year / $padMonth / $padDay"
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
                        taskTimeSelect.text = "$padTime : $padMinute"
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
            taskCategorySpinner.setSelection(categoryList.size - 1)

            taskCreateButton.setOnClickListener {
                val title = taskTitle.text.toString()
                val description = taskDescription.text.toString()
                val date = taskDateSelect.text.toString().dateTimeTrim()
                val time = taskTimeSelect.text.toString().dateTimeTrim()
                val category = taskCategorySpinner.selectedItemPosition
                todoViewModel.createTodo(title, description, date, time, category)
                this@TaskCreateFragment.findNavController()
                    .navigate(R.id.action_createTaskFragment_to_taskMainFragment)
                popUpAction.emptySnackBar(it, "Complete!")
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}