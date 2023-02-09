package com.msk.simpletodo.presentation.view.todo

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskEditBinding
import com.msk.simpletodo.domain.model.TaskDate
import com.msk.simpletodo.domain.util.getFullDateByString
import com.msk.simpletodo.presentation.util.KeyboardAction
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.util.dateTimeTrim
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import com.msk.simpletodo.service.NotificationFunction
import kotlinx.coroutines.flow.collectLatest
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.*


class TaskEditFragment : Fragment() {

    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var keyBoardAction: KeyboardAction
    private val cal by lazy { Calendar.getInstance() }
    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    private val taskDate by lazy { TaskDate() }
    private lateinit var todoEntity: TodoEntity

    private val notificationFunction by lazy { NotificationFunction(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        keyBoardAction = KeyboardAction(requireActivity(), binding.taskTitle)
        val categoryList = resources.getStringArray(R.array.category_array)

        lifecycleScope.launchWhenStarted {
            todoViewModel.taskDetail.collectLatest {
                todoEntity = it
                with(binding) {
                    taskTitle.setText(it.title, TextView.BufferType.EDITABLE)
                    taskDescription.setText(it.description, TextView.BufferType.EDITABLE)
                    taskDateSelect.text = it.date
                    taskTimeSelect.text = it.time
                    taskCategorySpinner.setSelection(categoryList.indexOf(it.category))
                    switch1.isChecked = it.notification
                }
            }
        }


        with(binding) {
            taskTitle.requestFocus()
            (activity as TodoActivity).hideBottomNavigation()

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
                        taskDate.copy(
                            selectedDate = LocalDate.of(
                                year,
                                monthOfYear + 1,
                                dayOfMonth
                            )
                        )
                    binding.taskDateSelect.text = newDate.currentDate.getFullDateByString()
                    binding.appbar.setExpanded(false)
                }

            binding.calendar.setOnDateChangedListener(dateChangeListener)

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

            binding.navTodayButton.setOnClickListener {
                binding.calendar.init(
                    taskDate.currentDate.year,
                    taskDate.currentDate.monthValue,
                    taskDate.currentDate.dayOfMonth,
                    dateChangeListener
                )
                binding.taskDateSelect.text = taskDate.currentDate.getFullDateByString()
            }

            val categoryAdapter = ArrayAdapter(
                requireContext(),
                R.layout.todo_category_item,
                categoryList
            )
            taskCategorySpinner.adapter = categoryAdapter


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
                val notify = switch1.isChecked
                val editTodoEntity = todoEntity.copy(
                    title = title,
                    description = description,
                    date = date,
                    time = time,
                    category = categoryString,
                    updatedAt = System.currentTimeMillis(),
                    notification = notify
                )
                if (notify) {
                    setNotification(todoEntity.createdAt.toInt(), title, "$date ${time}:00")
                } else {
                    deleteNotification(todoEntity.createdAt.toInt())
                }
                todoViewModel.editTodo(editTodoEntity)
                this@TaskEditFragment.findNavController()
                    .navigate(R.id.action_taskEditFragment_to_taskMainFragment)
                popUpAction.emptySnackBar(it, "Edit Complete!")
            }
        }

        return binding.root
    }

    fun setNotification(alarmCode: Int, content: String, time: String) {
        notificationFunction.callNotification(alarmCode, content, time)
    }

    fun deleteNotification(alarmCode: Int) {
        notificationFunction.cancelAlarm(alarmCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}