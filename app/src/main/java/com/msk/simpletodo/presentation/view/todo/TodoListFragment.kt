package com.msk.simpletodo.presentation.view.todo

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.FragmentTodoListBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.todo.TodoListAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel

class TodoListFragment : BaseFragment<FragmentTodoListBinding>(R.layout.fragment_todo_list) {

    private val todoViewModel: TodoViewModel by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val todoListAdapter: TodoListAdapter by lazy { TodoListAdapter() }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // get argument
        val args: Bundle? = arguments
        val position = args?.getInt("position")


        lifecycleScope.launchWhenStarted {
            // call categoryWithId function with args position
            todoViewModel.getTodoByCategoryId((position!!.toLong() + 1L))
//            todoViewModel.todoWithCategoryById.collectLatest { data ->
//                data?.let {
//                    val id = // set drawableIcon, adapter, result
//                        getDrawableId(requireContext(), data.todoCategory.categoryIcon)
//                    binding.todoCategoryIcon.setImageResource(id)
//                    binding.todoCategory = data
//                    todoListAdapter.setItem(data.todo.reversed())
//
//                    val done = it.todo.filter { it.done }.size // for progress bar
//                    val progressPt = ((done.toDouble() / it.todo.size.toDouble()) * 10).toInt()
//                    binding.progressPercent.text = "${progressPt * 10}%"
//                    binding.progressBar.setProgress(progressPt, true)
//                }
//            }
        }

        bind {
            binding.todoDateOfWeekRecycler.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            navAddTodoButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))

//             inside recycler item Event listener
//            todoListAdapter.setOnPassStateInterface(object : TodoListAdapter.OnPassStateInterface {
//                override fun passValue(todo: TodoEntity) = deleteTodo(todo)
//
//                override fun checkValue(todo: TodoEntity, checked: Boolean) =
//                    checkTodo(todo.copy(done = checked))
//            })
        }

        return view
    }

    /**
     * method from viewModel
     */
    fun deleteTodo(todo: TodoEntity) {
        todoViewModel.deleteTodo(todo)
    }

    fun checkTodo(todo: TodoEntity) {
        todoViewModel.checkTodo(todo)
    }
}









