package com.msk.simpletodo.presentation.view.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoAddTodoBinding
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.util.setCustomAdapter
import com.msk.simpletodo.presentation.view.base.UiState
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TodoAddTodoFragment : Fragment() {

    private var _binding: FragmentTodoAddTodoBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as TodoActivity).completeFragment(this@TodoAddTodoFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        // get position number
        val args: Bundle? = arguments
        val position = args?.getInt("position")

        // initial spinner item
        val todoSpinnerList = mutableListOf<String>()
        val todoImageList = mutableListOf<Int>()
        var todoCategoryType: Int = position!! + 1
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        _binding = FragmentTodoAddTodoBinding.inflate(inflater, container, false)
        binding.todoTitle.requestFocus()
        binding.backButton.setOnClickListener {
            (activity as TodoActivity).completeFragment(this)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                imm.showSoftInput(binding.todoTitle, 0)
                binding.addTodoButton.visibility = View.VISIBLE
            }

            todoViewModel.categoryWithTodo.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> state
                    is UiState.Success -> {
                        state.data.map { todoSpinnerList.add(it.todoCategory.category) }
                        state.data.map {
                            val id = getDrawableId(requireContext(), it.todoCategory.categoryIcon)
                            todoImageList.add(id)
                        }
                        binding.todoTypeSpinner.setCustomAdapter(
                            requireContext(), todoSpinnerList, todoImageList, position
                        )
                    }
                }
            }
        }

        binding.todoTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
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

        AnimationUtils.loadAnimation(requireContext(), R.anim.item_fade_up_700).also {
            binding.addTodoButton.startAnimation(it)
        }

        return binding.root
    }


    private fun createTodo(content: String, categoryType: Long) {
        todoViewModel.createTodo(content, categoryType)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}