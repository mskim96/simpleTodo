package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentTodoAddTodoBinding
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.util.setCustomAdapter
import com.msk.simpletodo.presentation.view.base.Result
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TodoAddTodoFragment : Fragment() {

    private var _binding: FragmentTodoAddTodoBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // get position number
        val args: Bundle? = arguments
        val position = args?.getInt("position")

        // initial spinner item
        val todoSpinnerList = mutableListOf<String>()
        val todoImageList = mutableListOf<Int>()
        var todoCategoryType: Int = position!! + 1

        _binding = FragmentTodoAddTodoBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            (activity as TodoActivity).completeFragment(this)
        }


        lifecycleScope.launch(Dispatchers.Main) {
            todoViewModel.categoryWithTodoResult.collect {
                when (it) {
                    is Result.Success -> {
                        it.data.map { todoSpinnerList.add(it.todoCategory.category) }
                        it.data.map {
                            val id = getDrawableId(requireContext(), it.todoCategory.categoryIcon)
                            todoImageList.add(id)
                        }
                        binding.todoTypeSpinner.setCustomAdapter(
                            requireContext(),
                            todoSpinnerList,
                            todoImageList,
                            position!!
                        )
                    }
                    else -> null
                }
            }
        }

        binding.todoTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // select TodoCategory Type
                    todoCategoryType = position + 1
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.addTodoButton.setOnClickListener {
            val content = binding.todoTitle.text.toString()
            val categoryType = todoCategoryType.toLong()
            createTodo(content, categoryType)
            (activity as TodoActivity).completeFragment(this)
        }

        return binding.root
    }


    private fun createTodo(content: String, categoryType: Long) {
        todoViewModel.createTodo(content, categoryType)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}