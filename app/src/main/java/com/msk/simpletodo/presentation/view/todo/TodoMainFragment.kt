package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.domain.model.CalendarDate
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.todo.CalendarAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

class TodoMainFragment : BaseFragment<FragmentTodoMainBinding>(R.layout.fragment_todo_main) {

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val calendarAdapter: CalendarAdapter by lazy {
        CalendarAdapter(requireContext()) { p ->
            Log.d(
                "TAG",
                ": $p"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        // Get CategoryWithTodo data when fragment create

        val view = super.onCreateView(inflater, container, savedInstanceState)
        // get argument
        val args: Bundle? = arguments
        val username = args?.getString("username")

        lifecycleScope.launchWhenStarted {
            todoViewModel.getCategoryWithTodo() // call category With TodoData
            todoViewModel.categoryWithTodo.collectLatest { state ->
                when (state) { // collect Latest from categoryWithTodo
                    is UiState.Loading -> state // send data to todoAdapter
                    is UiState.Success -> todoMainAdapter.submitList(state.data)
                    is UiState.Error -> Log.d("TAG", "onCreateView: ${state.message}")
                }
            }
        }
        // calendar recycler view setup
        calendarAdapter.setItem(CalendarDate.setDate())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.todoDateRecycler)
        binding.todoDateRecycler.setHasFixedSize(true)
        binding.todoDateRecycler.scrollToPosition(LocalDate.now().dayOfMonth - 2)

        bind {
            // binding default information
            date = convertTimestampToDate(null)
            time = convertTimestampToHour()

            todoDateRecycler.adapter = calendarAdapter
            // binding vm, ad
            vm = todoViewModel
            adapter = todoMainAdapter

//            todoSideNavButton.setOnClickListener {
//                (activity as TodoActivity).openNav()
//            }
        }
        return view
    }
}