package com.msk.simpletodo.presentation.viewModel.todo

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.TodoListItemBinding

class TodoListAdapter :
    RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    private var todoList = listOf<TodoEntity>()
    private lateinit var editClickListener: OnEditClickListener
    private lateinit var deleteClickListener: OnDeleteClickListener
    private lateinit var checkClickListener: OnCheckClickListener


    interface OnDeleteClickListener {
        fun onClick(todoItem: TodoEntity)
    }

    interface OnCheckClickListener {
        fun onClick(todoItem: TodoEntity)
    }

    interface OnEditClickListener {
        fun onClick(todoItem: TodoEntity)
    }

    fun setDeleteClickListener(itemClickListener: OnDeleteClickListener) {
        this.deleteClickListener = itemClickListener
    }

    fun setCheckClickListener(itemClickListener: OnCheckClickListener) {
        this.checkClickListener = itemClickListener
    }

    fun setEditClickListener(itemClickListener: OnEditClickListener) {
        this.editClickListener = itemClickListener
    }

    inner class TodoListViewHolder(val binding: TodoListItemBinding) :
        ViewHolder(binding.root) {
        fun bind(todoItem: TodoEntity) {
            binding.apply {
                todoItemTitle.text = todoItem.title
//                todoItemDescription.text = todoItem.description
                todoItemCategory.text = todoItem.category
                checkBox.isChecked = todoItem.done
                taskDeleteButton.setOnClickListener {
                    deleteClickListener.onClick(todoItem)
                }
                taskEditButton.setOnClickListener {
                    editClickListener.onClick(todoItem)
                }
                if (todoItem.notification) {
                    todoItemTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.todo_progress_dot_icon, 0,0,0)
                } else {
                    todoItemTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.todo_progress2_dot_icon, 0,0,0)
                }

                checkBox.setOnClickListener {
                    if (!checkBox.isChecked) checkClickListener.onClick(todoItem.copy(done = false))
                    else checkClickListener.onClick(todoItem.copy(done = true))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TodoListViewHolder(TodoListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todoList[position])
    }


    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setItem(data: List<TodoEntity>) {
        todoList = data
        notifyDataSetChanged()
    }
}