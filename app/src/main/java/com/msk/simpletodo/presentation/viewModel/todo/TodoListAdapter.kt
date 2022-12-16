package com.msk.simpletodo.presentation.viewModel.todo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.TodoListItemBinding

class TodoListAdapter :
    RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    private var listener: OnPassStateInterface? = null
    private var todoList = listOf<TodoEntity>()

    init {
        setHasStableIds(true)
    }

    interface OnPassStateInterface {
        fun passValue(todo: TodoEntity)
        fun checkValue(todo: TodoEntity, checked: Boolean)
    }

    fun setOnPassStateInterface(listener: OnPassStateInterface) {
        this.listener = listener
    }

    inner class TodoListViewHolder(val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoEntity) {
            binding.apply {
                todoContent.text = todoItem.content

                // after, change Binding Adapter
                if (todoItem.done) { // state checked
                    todoContent.paintFlags = todoContent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    todoContent.setTextColor(root.resources.getColor(R.color.placeholder))
                } else { // state unChecked
                    todoContent.paintFlags =
                        todoContent.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    todoContent.setTextColor(root.resources.getColor(R.color.font_default))
                }

                checkBox.isChecked = todoItem.done
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    listener?.checkValue(todoItem, isChecked)
                }
                todoDeleteButton.visibility = if (checkBox.isChecked) View.VISIBLE else View.GONE
                todoDeleteButton.setOnClickListener { listener?.passValue(todoItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    fun setItem(data: List<TodoEntity>) {
        todoList = data
        notifyDataSetChanged()
    }
}