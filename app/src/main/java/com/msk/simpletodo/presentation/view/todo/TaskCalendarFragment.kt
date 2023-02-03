package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskCalendarBinding
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.viewModel.todo.TodoListAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.DecimalFormat

class TaskCalendarFragment : Fragment() {

    private var _binding: FragmentTaskCalendarBinding? = null
    private val binding get() = _binding!!

    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val todoMainAdapter by lazy { TodoListAdapter() }
    private val auth by lazy { Firebase.auth }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCalendarBinding.inflate(inflater, container, false)

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

        lifecycleScope.launchWhenCreated {
            val format = DecimalFormat("00")
            val currentYear = binding.taskDateSelect.year
            val currentMonth = binding.taskDateSelect.month
            val currentDay = binding.taskDateSelect.dayOfMonth
            val padMonth = format.format(currentMonth + 1)
            val padDay = format.format(currentDay)
            todoViewModel.getTaskByDate("$currentYear/$padMonth/$padDay")
        }

        lifecycleScope.launchWhenStarted {
            todoViewModel.taskState.collectLatest {
                todoMainAdapter.setItem(it)
            }
        }

        binding.taskDateSelect.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val format = DecimalFormat("00")
            val padMonth = format.format(monthOfYear + 1)
            val padDay = format.format(dayOfMonth)
            todoViewModel.getTaskByDate("$year/$padMonth/$padDay")
        }

        todoMainAdapter.setDeleteClickListener(object : TodoListAdapter.OnDeleteClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.deleteTodo(todoItem)
                popUpAction.emptySnackBar(binding.textView20, "Delete Complete")
            }
        })

        todoMainAdapter.setEditClickListener(object : TodoListAdapter.OnEditClickListener {
            override fun onClick(todoItem: TodoEntity) {
                popUpAction.emptySnackBar(binding.textView20, "Can't edit task on Calendar page")
            }
        })

        todoMainAdapter.setCheckClickListener(object : TodoListAdapter.OnCheckClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.checkTodo(todoItem)
            }
        })

        binding.todoTaskRecycler.adapter = todoMainAdapter

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}