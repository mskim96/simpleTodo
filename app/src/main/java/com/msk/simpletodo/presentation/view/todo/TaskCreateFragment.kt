package com.msk.simpletodo.presentation.view.todo

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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTaskCreateBinding
import com.msk.simpletodo.domain.model.TaskDate
import com.msk.simpletodo.domain.util.getFullDateByString
import com.msk.simpletodo.domain.util.getTimeByString
import com.msk.simpletodo.presentation.util.KeyboardAction
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.util.dateTimeTrim
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class TaskCreateFragment : Fragment() {

    private var _binding: FragmentTaskCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var keyBoardAction: KeyboardAction
    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val cal by lazy { Calendar.getInstance() }
    private val taskDate by lazy { TaskDate() }

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
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.appbar.setExpanded(false)
        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                binding.taskDateSelect.setOnClickListener {
                    keyBoardAction.hideKeyboard()
                    if (state == State.EXPANDED) {
                        binding.appbar.setExpanded(false)
                    } else {
                        binding.appbar.setExpanded(true)
                    }
                }
            }
        })

        val dateChangeListener: (view: View, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit =
            { _, year, monthOfYear, dayOfMonth ->
                val newDate =
                    taskDate.copy(selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth))
                binding.taskDateSelect.text = newDate.currentDate.getFullDateByString()
                binding.appbar.setExpanded(false)
            }

        binding.calendar.setOnDateChangedListener(dateChangeListener)

        binding.navTodayButton.setOnClickListener {
            binding.calendar.init(
                taskDate.currentDate.year,
                taskDate.currentDate.monthValue,
                taskDate.currentDate.dayOfMonth,
                dateChangeListener
            )
            binding.taskDateSelect.text = taskDate.currentDate.getFullDateByString()
        }


        with(binding) {
            // Focus title and hide bottom navigation
            taskTitle.requestFocus()
            taskTitle.setOnClickListener { appbar.setExpanded(false) }
            (activity as TodoActivity).hideBottomNavigation()

            taskDateSelect.text = TaskDate().currentDate.getFullDateByString()

            taskTimeSelect.text = LocalDateTime.now().getTimeByString()

            taskTimeSelect.setOnClickListener {
                appbar.setExpanded(false)
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
            taskCategorySpinner.setSelection(0)

            taskCreateButton.setOnClickListener {
                val title = taskTitle.text.toString()
                val date = taskDateSelect.text.toString()
                val time = taskTimeSelect.text.toString().dateTimeTrim()
                val category = taskCategorySpinner.selectedItemPosition
                val description = taskDescription.text.toString()
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