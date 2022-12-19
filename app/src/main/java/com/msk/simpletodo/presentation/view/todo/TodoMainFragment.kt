package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
import kotlinx.coroutines.flow.collectLatest

class TodoMainFragment : BaseFragment<FragmentTodoMainBinding>(R.layout.fragment_todo_main) {

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

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

        /**
         * TodoMainFragment Animation
         */
        AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_500).also {
            binding.todoMainGreeting.startAnimation(it)
            binding.todoMainUsername.startAnimation(it)
        }

        AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_1000).also {
            binding.textView.startAnimation(it)
            binding.todoMainTodayTodoCount.startAnimation(it)
        }

        return view
    }
}