package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDOW
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.view.base.Result
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TodoMainFragment : Fragment() {

    private lateinit var binding: FragmentTodoMainBinding

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_todo_main, container, false)

        todoViewModel.getCategoryWithTodo()

        val args: Bundle? = arguments
        val username = args?.getString("username")

        lifecycleScope.launch(Dispatchers.Main) {
            todoViewModel.categoryWithTodoResult.collect {
                when (it) {
                    is Result.Success -> {
                        todoMainAdapter.submitList(it.data)
                    }
                    else -> null
                }
            }
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            this.username = username
            this.date = convertTimestampToDate(null)
            this.time = convertTimestampToHour()

            this.vm = todoViewModel
            this.adapter = todoMainAdapter
        }

        // for recycler view

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}