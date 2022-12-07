package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel

class TodoMainFragment : Fragment() {

    private var _binding: FragmentTodoMainBinding? = null
    private val binding get() = _binding!!

    private val todoMainAdapter: TodoMainAdapter by lazy { TodoMainAdapter() }
    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoMainBinding.inflate(inflater, container, false)

        // get Argument from TodoActivity
        val args: Bundle? = arguments
        if (args != null) {
            val username = args.getString("username")
            binding.todoMainUsername.text = username
            binding.todoToday.text = "TODAY: ${convertTimestampToDate()}"
            binding.todoGreeting.text = when (convertTimestampToHour()) {
                in 6..11 -> "Good morning ,"
                in 12..17 -> "Good Afternoon ,"
                else -> "Good Evening ,"
            }
        }

        todoViewModel.todoData.observe(viewLifecycleOwner, Observer {
            binding.vm = todoViewModel
        })


        // for recycler view
        binding.todoMainRecyclerView.adapter = todoMainAdapter
        binding.todoMainRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}