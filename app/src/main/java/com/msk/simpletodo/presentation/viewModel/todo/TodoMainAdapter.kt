package com.msk.simpletodo.presentation.viewModel.todo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.databinding.TodoMainItemBinding
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class TodoMainAdapter() :
    ListAdapter<TodoCategoryWithTodo, TodoMainAdapter.ToDoMainViewHolder>(DiffUtilCallback()) {

    init {
        setHasStableIds(true)
    }

    inner class ToDoMainViewHolder(val binding: TodoMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoCategory: TodoCategoryWithTodo) {
            val id =
                getDrawableId(binding.root.context, "${todoCategory.todoCategory.categoryIcon}")
            binding.todoMainIcon.setImageResource(id)
            binding.todoCategory = todoCategory
            binding.todoFrameCard.setOnClickListener {
                (binding.root.context.findActivity() as TodoActivity).setListFragment(
                    adapterPosition
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoMainViewHolder {
        return ToDoMainViewHolder(
            TodoMainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ToDoMainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun Context.findActivity(): Context {
        while (this is ContextWrapper && this !is Activity) {
            return baseContext
        }
        return this
    }

}