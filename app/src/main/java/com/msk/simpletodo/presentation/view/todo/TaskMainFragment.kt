package com.msk.simpletodo.presentation.view.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskMainBinding
import com.msk.simpletodo.domain.model.CalendarDate
import com.msk.simpletodo.domain.model.TaskDate
import com.msk.simpletodo.domain.util.getFullDateByString
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.todo.CalendarAdapter
import com.msk.simpletodo.presentation.viewModel.todo.SwipeHelperCallback
import com.msk.simpletodo.presentation.viewModel.todo.TodoListAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import com.msk.simpletodo.service.NotificationFunction
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TaskMainFragment : BaseFragment<FragmentTaskMainBinding>(R.layout.fragment_task_main) {

    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val calendarAdapter by lazy {
        CalendarAdapter(requireContext()) { p -> getTaskByDate(p) }
    }
    private val todoMainAdapter by lazy { TodoListAdapter() }
    private val auth by lazy { Firebase.auth }

    private val popUpAction: PopUpAction by lazy { PopUpAction() }
    private val taskDate by lazy { TaskDate() }

    private val notificationFunction by lazy { NotificationFunction(requireContext()) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as TodoActivity).setBottomNavigation()

        val navigationView = binding.navView
        val getNavigationView = navigationView.getHeaderView(0)
        getNavigationView.findViewById<TextView>(R.id.navHeaderUsername).text =
            auth.currentUser?.email
        val drawerLayout = binding.mainDrawerLayout
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.taskMainFragment,
                R.id.taskCalendarFragment,
                R.id.taskAuthFragment
            ), drawerLayout
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        binding.appbar.setExpanded(false)
        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED) {
                    binding.arrowImageView.animate().rotation(-180f).setDuration(150).withLayer()
                } else {
                    binding.arrowImageView.animate().rotation(0f).setDuration(150).withLayer()
                }
                binding.backsupport.setOnClickListener {
                    if (state == State.EXPANDED) {
                        binding.appbar.setExpanded(false)
                    } else {
                        binding.appbar.setExpanded(true)
                    }
                }
            }
        })

        binding.monthname.text = TaskDate().currentDate.month.toString()

        val dateChangeListener: (view: View, year: Int, monthOfYear: Int, dayOfMonth: Int) -> Unit =
            { _, year, monthOfYear, dayOfMonth ->
                val newDate =
                    taskDate.copy(selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth))
                binding.monthname.text = newDate.currentDate.month.toString()
                calendarAdapter.setItem(CalendarDate.setDate(newDate))
                binding.todoDateRecycler.smoothScrollToPosition(newDate.currentDate.dayOfMonth - 1)
                calendarAdapter.selectedPosition = newDate.currentDate.dayOfMonth - 1
                todoViewModel.getTaskByDate(newDate.currentDate.getFullDateByString())
            }

        binding.calendar.setOnDateChangedListener(dateChangeListener)

        binding.navTodayButton.setOnClickListener {
            binding.monthname.text = taskDate.currentDate.month.toString()
            calendarAdapter.setItem(CalendarDate.setDate(taskDate))
            binding.todoDateRecycler.smoothScrollToPosition(taskDate.currentDate.dayOfMonth - 1)
            calendarAdapter.selectedPosition = taskDate.currentDate.dayOfMonth - 1
            todoViewModel.getTaskByDate(taskDate.currentDate.getFullDateByString())
            binding.calendar.init(
                taskDate.currentDate.year,
                taskDate.currentDate.monthValue,
                taskDate.currentDate.dayOfMonth,
                dateChangeListener
            )
        }

        lifecycleScope.launchWhenStarted {
            todoViewModel.getTaskByDate(taskDate.currentDate.getFullDateByString())
            todoViewModel.taskState.collectLatest {
                todoMainAdapter.setItem(it)
            }
        }


        // calendar recycler view setup
        calendarAdapter.setItem(CalendarDate.setDate(taskDate))
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.todoDateRecycler)

        binding.todoDateRecycler.setHasFixedSize(true)
        binding.todoDateRecycler.scrollToPosition(LocalDate.now().dayOfMonth - 1)

        binding.navTodoAddButton.setOnClickListener {
            this.findNavController()
                .navigate(R.id.action_taskMainFragment_to_createTaskFragment)
        }


        val swipeHelperCallback = SwipeHelperCallback()
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoTaskRecycler)
        binding.todoTaskRecycler.setOnTouchListener { _, motionEvent ->
            swipeHelperCallback.removePreviousClamp(binding.todoTaskRecycler)
            false
        }

        todoMainAdapter.setDeleteClickListener(object : TodoListAdapter.OnDeleteClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.deleteTodo(todoItem)
                if (todoItem.notification) deleteNotification(todoItem.createdAt.toInt())
                popUpAction.emptySnackBar(binding.navTodoAddButton, "Delete Complete")
                swipeHelperCallback.removePreviousClamp(binding.todoTaskRecycler)
            }
        })

        todoMainAdapter.setEditClickListener(object : TodoListAdapter.OnEditClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.emitTask(todoItem)
                val action =
                    TaskMainFragmentDirections.actionTaskMainFragmentToTaskEditFragment()
                this@TaskMainFragment.findNavController().navigate(action)
            }
        })

        todoMainAdapter.setCheckClickListener(object : TodoListAdapter.OnCheckClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.checkTodo(todoItem)
            }
        })

        bind {
            // binding default information
            todoDateRecycler.adapter = calendarAdapter
            todoTaskRecycler.adapter = todoMainAdapter
            // binding vm, ad
            vm = todoViewModel
        }
        return view
    }

    fun getDate(current: Int): String {
        val cal = Calendar.getInstance()
        val today = cal.get(Calendar.DAY_OF_MONTH)
        var amount = current - today
        cal.add(Calendar.DATE, amount)
        return SimpleDateFormat("yyyy/MM/dd", Locale("en", "ja")).format(cal.time)
    }

    fun getTaskByDate(position: Int) {
        val date = getDate(position)
        todoViewModel.getTaskByDate(date)
    }

    fun deleteNotification(alarmCode: Int) {
        notificationFunction.cancelAlarm(alarmCode)
    }
}
