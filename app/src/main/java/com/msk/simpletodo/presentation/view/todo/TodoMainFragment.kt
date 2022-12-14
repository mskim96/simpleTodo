package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoMainFragment : BaseFragment<FragmentTodoMainBinding>(R.layout.fragment_todo_main) {

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        // Get CategoryWithTodo data when fragment create
        todoViewModel.getCategoryWithTodo()

        val view = super.onCreateView(inflater, container, savedInstanceState)
        // get argument
        val args: Bundle? = arguments
        val username = args?.getString("username")

        // collect categoryWithTodo data
        lifecycleScope.launch(Dispatchers.Main) {
            todoViewModel.categoryWithTodo.collect {
                when (it) {
                    is UiState.Loading -> it
                    is UiState.Success -> todoMainAdapter.submitList(it.data)
                    is UiState.Fail -> it.error
                }
            }
        }

        bind {
            // binding default information
            name = username
            date = convertTimestampToDate(null)
            time = convertTimestampToHour()

            // binding vm, ad
            vm = todoViewModel
            adapter = todoMainAdapter
        }

        return view
    }
}