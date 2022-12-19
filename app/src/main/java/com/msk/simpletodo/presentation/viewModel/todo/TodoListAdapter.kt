package com.msk.simpletodo.presentation.viewModel.todo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
        ViewHolder(binding.root) {
        fun bind(todoItem: TodoEntity) {
            binding.apply {
                todoContent.text = todoItem.content
                checkBox.isChecked = todoItem.done

                // after, change Binding Adapter
                if (todoItem.done) { // state checked
                    todoContent.paintFlags = todoContent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    todoContent.setTextColor(root.resources.getColor(R.color.placeholder))
                } else { // state unChecked
                    todoContent.paintFlags =
                        todoContent.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    todoContent.setTextColor(root.resources.getColor(R.color.font_default))
                }

                /**
                 * Delete button Animation
                 */
                if (checkBox.isChecked) { // Delete Button Event
                    todoDeleteButton.animate().alpha(1.0f).duration = 800
                    todoDeleteButton.visibility = View.VISIBLE // delete button animation
                } else {
                    todoDeleteButton.animate().alpha(0.0f).duration = 800
                    todoDeleteButton.visibility = View.GONE
                }

                /**
                 * complete debug.
                 * When there are multiple checks in a row, if you delete the middle item,
                 * the check of the item at the bottom of the checked item is released
                 * isChecked is The status of the last item has been saved, it always return false
                 * then add logic compare todoItem and todoList item position, only when it matches
                 * return value
                 */
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (todoItem == todoList[adapterPosition]) {
                        listener?.checkValue(todoItem, isChecked)
                    }
                }

                todoDeleteButton.setOnClickListener { // send todoEntity to TodoList fragment
                    if (todoItem == todoList[adapterPosition]) { // only todoItem match todoList(position)
                        listener?.passValue(todoItem) // pass value
                    }
                }
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