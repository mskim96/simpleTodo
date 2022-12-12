package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoListBinding
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.view.base.Result
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding

    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_todo_list, container, false)

        val args: Bundle? = arguments
        val position = args?.getInt("position")

        lifecycleScope.launch(Dispatchers.IO) {
            todoViewModel.getTodoByCategoryId((position!!.toLong() + 1L))

            todoViewModel.todoWithCategoryById.collect {
                when (it) {
                    is Result.Success -> {
                        val id = getDrawableId(
                            requireContext(),
                            it.data.todoCategory.categoryIcon
                        )
                        binding.todoCategoryIcon.setImageResource(id)
                        binding.todoCategory = it.data
                    }
                    else -> null
                }
            }
        }

        binding.navAddTodoButton.setOnClickListener {
            (activity as TodoActivity).setFragmentAddToDo(position!!)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}