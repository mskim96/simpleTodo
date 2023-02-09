package com.msk.simpletodo.presentation.viewModel.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.TodoRecentItemBinding

class TaskRecentAdapter(val edit: (TodoEntity) -> Unit, val remove: (TodoEntity) -> Unit) :
    RecyclerView.Adapter<TaskRecentAdapter.RecentViewHolder>() {

    private var recentList: List<TodoEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecentViewHolder(TodoRecentItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(recentList[position])
    }

    override fun getItemCount() = recentList.size

    inner class RecentViewHolder(private val binding: TodoRecentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TodoEntity) {
            binding.apply {
                taskDate.text = task.date
                taskTitle.text = task.title
                taskTime.text = task.time
                textView.text = task.category
                taskEditButton.setOnClickListener { edit(task) }
                taskDeleteButton.setOnClickListener { remove(task) }
            }
        }
    }

    @Suppress("notifyDataSetChanged")
    fun setItem(dataList: List<TodoEntity>) {
        recentList = dataList
        notifyDataSetChanged()
    }
}