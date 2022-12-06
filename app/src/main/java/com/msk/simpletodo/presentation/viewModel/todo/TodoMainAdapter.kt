package com.msk.simpletodo.presentation.viewModel.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.TodoMainItemBinding
import com.msk.simpletodo.domain.todo.model.TodoFrame
import com.msk.simpletodo.domain.todo.model.TodoType

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
                }
                TodoType.Work -> {
                    binding.todoMainIcon.setImageResource(R.drawable.todo_work_icon)
                }
                TodoType.Study -> {
                    binding.todoMainIcon.setImageResource(R.drawable.todo_study_icon)
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
}