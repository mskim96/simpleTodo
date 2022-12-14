package com.msk.simpletodo.presentation.viewModel.todo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.databinding.TodoMainItemBinding
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class TodoMainAdapter() :
    ListAdapter<CategoryWithTodo, TodoMainAdapter.ToDoMainViewHolder>(DiffUtilCallback()) {

    init {
        setHasStableIds(true)
    }

    inner class ToDoMainViewHolder(val binding: TodoMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoCategory: CategoryWithTodo) {
            val id = getDrawableId(binding.root.context, todoCategory.todoCategory.categoryIcon)
            binding.apply {
                todoMainIcon.setImageResource(id)
                todoCategoryItem = todoCategory
                todoFrameCard.setOnClickListener {
                    (binding.root.context.findActivity() as TodoActivity).setListFragment(
                        adapterPosition
                    )
                }
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // extension function for use activity method (hilt)
    fun Context.findActivity(): Context {
        while (this is ContextWrapper && this !is Activity) {
            return baseContext
        }
        return this
    }

}