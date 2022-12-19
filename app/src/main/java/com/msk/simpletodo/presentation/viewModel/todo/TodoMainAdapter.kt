package com.msk.simpletodo.presentation.viewModel.todo

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.databinding.TodoMainItemBinding
import com.msk.simpletodo.presentation.util.findActivity
import com.msk.simpletodo.presentation.util.getDrawableId
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class TodoMainAdapter() :
    ListAdapter<CategoryWithTodo, TodoMainAdapter.ToDoMainViewHolder>(DiffUtilCallback()) {

    init {
        setHasStableIds(true)
    }

    inner class ToDoMainViewHolder(val binding: TodoMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(todoCategory: CategoryWithTodo) {
            val id = getDrawableId(binding.root.context, todoCategory.todoCategory.categoryIcon)
            binding.apply { // set card information
                todoMainIcon.setImageResource(id)
                todoCategoryItem = todoCategory // for binding
                val done = todoCategory.todo.filter { it.done }.size // filter todoItem is done
                val progressPt = (done.toDouble() / todoCategory.todo.size.toDouble() * 10).toInt()
                progressBar.setProgress(progressPt, true) // set progress bar value
                itemView.setOnClickListener {
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ToDoMainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}