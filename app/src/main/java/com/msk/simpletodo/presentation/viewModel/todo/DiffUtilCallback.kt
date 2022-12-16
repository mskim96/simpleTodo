package com.msk.simpletodo.presentation.viewModel.todo

import androidx.recyclerview.widget.DiffUtil
import com.msk.simpletodo.data.model.todo.CategoryWithTodo

class DiffUtilCallback() : DiffUtil.ItemCallback<CategoryWithTodo>() {
    override fun areItemsTheSame(
        oldItem: CategoryWithTodo,
        newItem: CategoryWithTodo,
    ) =
        oldItem.todo.map { it.done } == newItem.todo.map { it.done }


    override fun areContentsTheSame(
        oldItem: CategoryWithTodo,
        newItem: CategoryWithTodo,
    ) =
        oldItem.todo == newItem.todo

}