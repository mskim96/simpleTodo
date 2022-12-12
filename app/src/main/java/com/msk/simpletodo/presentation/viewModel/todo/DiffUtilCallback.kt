package com.msk.simpletodo.presentation.viewModel.todo

import androidx.recyclerview.widget.DiffUtil
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo

class DiffUtilCallback() : DiffUtil.ItemCallback<TodoCategoryWithTodo>() {
    override fun areItemsTheSame(
        oldItem: TodoCategoryWithTodo,
        newItem: TodoCategoryWithTodo
    ) =
        oldItem.todo.size == newItem.todo.size


    override fun areContentsTheSame(
        oldItem: TodoCategoryWithTodo,
        newItem: TodoCategoryWithTodo
    ) =
        oldItem.todo == oldItem.todo

}