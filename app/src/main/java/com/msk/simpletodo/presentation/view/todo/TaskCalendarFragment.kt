package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskCalendarBinding
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.viewModel.todo.SwipeHelperCallback
import com.msk.simpletodo.presentation.viewModel.todo.TaskRecentAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoListAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskCalendarFragment : Fragment() {

    private var _binding: FragmentTaskCalendarBinding? = null
    private val binding get() = _binding!!

    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val todoMainAdapter by lazy { TodoListAdapter() }
    private val taskRecentAdapter by lazy {
        TaskRecentAdapter(
            edit = { task -> editTask(task) },
            remove = { task -> deleteTask(task) })
    }
    private val auth by lazy { Firebase.auth }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCalendarBinding.inflate(inflater, container, false)
        (activity as TodoActivity).setBottomNavigation()

        /**
         * set Navigation and toolbar
         */
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

        binding.taskSearch.doAfterTextChanged {
            if (binding.taskSearch.text.isNullOrBlank()) {
                binding.textClearButton.visibility = View.GONE
                binding.constraintLayout3.visibility = View.VISIBLE
            } else {
                lifecycleScope.launch {
                    todoViewModel.getTaskByQuery(it.toString())
                }
                binding.textClearButton.visibility = View.VISIBLE
                binding.constraintLayout3.visibility = View.GONE
            }
        }

        /**
         * get task item from flow
         * and set Recyclerview when launch start
         */
        lifecycleScope.launchWhenStarted {
            todoViewModel.getTaskByRecent()

            launch {
                todoViewModel.taskRecentState.collectLatest {
                    if (it.isNotEmpty()) {
                        binding.constraintLayout3.isVisible = true
                        taskRecentAdapter.setItem(it)
                    } else binding.constraintLayout3.isVisible = false

                }
            }

            launch {
                todoViewModel.taskSearchState.collectLatest {
                    todoMainAdapter.setItem(it)
                }
            }
        }


        /**
         * Recyclerview item click listener
         * with interface method (listener)
         */
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

        val swipeHelperCallback = SwipeHelperCallback()
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoTaskRecycler)
        binding.todoTaskRecycler.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.todoTaskRecycler)
            false
        }

        binding.todoTaskRecycler.adapter = todoMainAdapter
        binding.taskRecentRecycler.adapter = taskRecentAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun editTask(task: TodoEntity) {
        todoViewModel.emitTask(task)
        val action =
            TaskCalendarFragmentDirections.actionTaskCalendarFragmentToTaskEditFragment()
        this@TaskCalendarFragment.findNavController().navigate(action)
    }

    private fun deleteTask(task: TodoEntity) {
        todoViewModel.deleteTodo(task)
    }
}