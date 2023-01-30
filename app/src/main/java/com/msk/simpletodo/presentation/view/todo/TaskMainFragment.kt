package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTaskMainBinding
import com.msk.simpletodo.domain.model.CalendarDate
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.todo.CalendarAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoListAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TaskMainFragment : BaseFragment<FragmentTaskMainBinding>(R.layout.fragment_task_main) {

    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val calendarAdapter by lazy { CalendarAdapter(requireContext()) { p -> getTaskByDate(p) } }
    private val todoMainAdapter by lazy { TodoListAdapter() }

    private val popUpAction: PopUpAction by lazy { PopUpAction() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as TodoActivity).setBottomNavigation()

        val cal = Calendar.getInstance()
        val today = SimpleDateFormat("yyyy/MM/dd", Locale("en", "ja")).format(cal.time)
        val todayOther = SimpleDateFormat("dd, MMM, yyyy", Locale("en", "ja")).format(cal.time)

        lifecycleScope.launchWhenStarted {
            todoViewModel.getTaskByDate(today)
            todoViewModel.taskState.collectLatest {
                todoMainAdapter.setItem(it)
            }
        }
        // calendar recycler view setup
        calendarAdapter.setItem(CalendarDate.setDate())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.todoDateRecycler)
        binding.todoDateRecycler.setHasFixedSize(true)
        binding.todoDateRecycler.scrollToPosition(LocalDate.now().dayOfMonth - 2)

        binding.navTodoAddButton.setOnClickListener {
            this.findNavController()
                .navigate(R.id.action_taskMainFragment_to_createTaskFragment)
        }

        todoMainAdapter.setDeleteClickListener(object : TodoListAdapter.OnDeleteClickListener {
            override fun onClick(todoItem: TodoEntity) {
                todoViewModel.deleteTodo(todoItem)
                popUpAction.emptySnackBar(binding.navTodoAddButton, "Delete Complete")
            }
        })

        todoMainAdapter.setEditClickListener(object : TodoListAdapter.OnEditClickListener {
            override fun onClick(todoItem: TodoEntity) {
                val action =
                    TaskMainFragmentDirections.actionTaskMainFragmentToTaskEditFragment(todoItem)
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
            taskMainToday.text = todayOther
            todoDateRecycler.adapter = calendarAdapter
            todoTaskRecycler.adapter = todoMainAdapter
            // binding vm, ad
            vm = todoViewModel
        }

        lifecycleScope.launch(Dispatchers.Main) {
            todoViewModel.taskState.collectLatest {
                emptyData(it)
            }
        }

        return view
    }

    fun emptyData(list: List<TodoEntity>) {
        if (list.isEmpty()) binding.emptyPage.visibility = View.VISIBLE
        else binding.emptyPage.visibility = View.GONE
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
}
