package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.TodoListItemBinding

class TodoListAdapter() :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    private var listener: OnSendStateInterface? = null
    private var todoList = mutableListOf<TodoEntity>()

    init {
        setHasStableIds(true)
    }

    interface OnSendStateInterface {
        fun sendValue(position: Int)
    }

    fun setOnStateInterface(listener: OnSendStateInterface) {
        this.listener = listener
    }


    inner class TodoViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentTodo: TodoEntity) {
            binding.todo = currentTodo
            binding.todoDeleteButton.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener?.sendValue(pos)
                    todoList.removeAt(pos)
                    notifyDataSetChanged()
                }
            }
            DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setItem(data: List<TodoEntity>) {
        todoList = data.toMutableList()
        notifyDataSetChanged()
    }
}