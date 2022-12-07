package com.msk.simpletodo.presentation.viewModel.todo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.TodoMainItemBinding
import com.msk.simpletodo.domain.todo.model.TodoFrame
import com.msk.simpletodo.domain.todo.model.TodoType
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class TodoMainAdapter() : RecyclerView.Adapter<TodoMainAdapter.ToDoMainViewHolder>() {

    private val person = TodoFrame(type = TodoType.Person, "Person")
    private val work = TodoFrame(type = TodoType.Work, "Work")
    private val study = TodoFrame(type = TodoType.Study, "Study")
    private val listTodoFrame = mutableListOf(person, work, study)

    inner class ToDoMainViewHolder(val binding: TodoMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoFrame: TodoFrame) {
            when (todoFrame.type) {
                TodoType.Person -> {
                    binding.todoMainIcon.setImageResource(R.drawable.todo_user_icon)
                    binding.todoFrameCard.setOnClickListener {
                        (binding.root.context.findActivity() as TodoActivity).setFragment(todoFrame.type)
                    }
                }
                TodoType.Work -> {
                    binding.todoMainIcon.setImageResource(R.drawable.todo_work_icon)
                    binding.todoFrameCard.setOnClickListener {
                        (binding.root.context.findActivity() as TodoActivity).setFragment(todoFrame.type)
                    }
                }
                TodoType.Study -> {
                    binding.todoMainIcon.setImageResource(R.drawable.todo_study_icon)
                    binding.todoFrameCard.setOnClickListener {
                        (binding.root.context.findActivity() as TodoActivity).setFragment(todoFrame.type)
                    }
                }
            }
            binding.todo = todoFrame
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoMainViewHolder {
        val binding =
            TodoMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoMainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoMainViewHolder, position: Int) {
        return holder.bind(listTodoFrame[position])
    }

    override fun getItemCount(): Int {
        return listTodoFrame.size
    }

    fun Context.findActivity(): Context {
        while (this is ContextWrapper && this !is Activity) {
            return baseContext
        }
        return this
    }
}