package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoAddTodoBinding
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import com.msk.simpletodo.presentation.viewModel.todo.setCustomAdapter


class TodoAddTodoFragment : Fragment() {

    private var _binding: FragmentTodoAddTodoBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private var todoType: String? = null
    private var userId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodoAddTodoBinding.inflate(inflater, container, false)

        // for spinner
        var todoTypeItem = mutableListOf("Person", "Work", "Study")
        var todoTypeImage = mutableListOf(
            R.drawable.todo_user_icon,
            R.drawable.todo_work_icon,
            R.drawable.todo_study_icon
        )

        binding.todoTypeSpinner.setCustomAdapter(requireContext(), todoTypeItem, todoTypeImage)
        binding.todoTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (todoTypeItem[position]) {
                        "Person" -> {
                            todoType = todoTypeItem[position]
                        }
                        "Work" -> {
                            todoType = todoTypeItem[position]
                        }
                        "Study" -> {
                            todoType = todoTypeItem[position]
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.addTodoButton.setOnClickListener {
            val title = binding.todoTitle.text.toString()
            val todoType = todoType
            val userId = userId
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}