package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTaskMainBinding
import com.msk.simpletodo.domain.model.CalendarDate
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.todo.CalendarAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import java.time.LocalDate


class TaskMainFragment : BaseFragment<FragmentTaskMainBinding>(R.layout.fragment_task_main) {

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as TodoActivity).setBottomNavigation()

        lifecycleScope.launchWhenStarted {
//            todoViewModel.categoryWithTodo.collectLatest { state ->
//                when (state) { // collect Latest from categoryWithTodo
//                    is UiState.Loading -> state // send data to todoAdapter
//                    is UiState.Success -> todoMainAdapter.submitList(state.data)
//                    is UiState.Error -> Log.d("TAG", "onCreateView: ${state.message}")
//                }
//            }
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

        bind {
            // binding default information
            date = convertTimestampToDate(null)
            time = convertTimestampToHour()

            todoDateRecycler.adapter = calendarAdapter
            // binding vm, ad
            vm = todoViewModel

//            todoSideNavButton.setOnClickListener {
//                (activity as TodoActivity).openNav()
//            }
        }


        return view
    }
}