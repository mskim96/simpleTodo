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
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoMainFragment : Fragment() {

    private lateinit var binding: FragmentTodoMainBinding

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_todo_main, container, false)

//        val id = context?.resources?.getIdentifier("todo_user_icon", "drawable",requireContext().packageName)
//        Log.d("TAG", "onCreateView: ${R.drawable.todo_user_icon}")
//        Log.d("TAG", "onCreateView: ${id}")

        val args: Bundle? = arguments
        val username = args?.getString("username")

//        val resultTodayTodo = lifecycleScope.launch(Dispatchers.IO) {
//            var result = 0
//            todoViewModel.categoryWithTodoResult.collect {
//                it.map {
//                    result += it.todo.size
//                }
//            }
//        }

        lifecycleScope.launch(Dispatchers.IO) {
            todoViewModel.categoryWithTodoResult.collect {
                Log.d("TAG", "onCreateView: $it")
            }
        }

        binding.apply {
            lifecycleOwner = this@TodoMainFragment

            this.username = username
            this.date = convertTimestampToDate()
            this.time = convertTimestampToHour()
            this.vm = todoViewModel
        }

        // for recycler view
        binding.todoMainRecyclerView.adapter = todoMainAdapter
        binding.todoMainRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}