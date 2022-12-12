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
import com.msk.simpletodo.databinding.FragmentTodoMainBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDate
import com.msk.simpletodo.presentation.util.convertTimestampToHour
import com.msk.simpletodo.presentation.view.base.Result
import com.msk.simpletodo.presentation.viewModel.todo.TodoMainAdapter
import com.msk.simpletodo.presentation.viewModel.todo.TodoViewModel
import kotlinx.coroutines.Dispatchers
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

        lifecycleScope.launch(Dispatchers.IO) {
            todoViewModel.categoryWithTodoResult.collect {
                when (it) {
                    is Result.Success -> {
                        Log.d("TAG", "onCreateView: ${it.data}")
                        todoMainAdapter.submitList(it.data)
                    }
                    else -> null
                }
            }
        }

        binding.apply {
            lifecycleOwner = this@TodoMainFragment

            this.username = username
            this.date = convertTimestampToDate()
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

//[
//TodoCategoryWithTodo(todoCategory=TodoCategory(tid=1, category=Personal, categoryIcon=todo_user_icon),
//todo=[
//TodoEntity(tid=1, content=Person Sample1, createdAt=1670827218390, updatedAt=1670827218390, done=false, fkCategory=1),
//TodoEntity(tid=2, content=Personal 2, createdAt=1670827375654, updatedAt=1670827375654, done=false, fkCategory=1)
//]
//),
//
//TodoCategoryWithTodo(todoCategory=TodoCategory(tid=2, category=Work, categoryIcon=todo_work_icon),
//todo=[
//TodoEntity(tid=3, content=Work 1, createdAt=1670827389222, updatedAt=1670827389222, done=false, fkCategory=2)]
//),
//
//TodoCategoryWithTodo(todoCategory=TodoCategory(tid=3, category=Study, categoryIcon=todo_study_icon),
//todo=[
//TodoEntity(tid=4, content=Study 1, createdAt=1670827398988, updatedAt=1670827398988, done=false, fkCategory=3),
//TodoEntity(tid=6, content=Study2 from other, createdAt=1670827419393, updatedAt=1670827419393, done=false, fkCategory=3)]),
//
//TodoCategoryWithTodo(todoCategory=TodoCategory(tid=4, category=Other, categoryIcon=todo_other_icon),
//todo=[
//TodoEntity(tid=5, content=Other 1, createdAt=1670827407456, updatedAt=1670827407456, done=false, fkCategory=4)])
//]