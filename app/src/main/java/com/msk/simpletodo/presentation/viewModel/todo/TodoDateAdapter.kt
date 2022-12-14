package com.msk.simpletodo.presentation.viewModel.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.databinding.TodoDateItemBinding
import com.msk.simpletodo.presentation.util.convertTimestampToDate

class TodoDateAdapter() : RecyclerView.Adapter<TodoDateAdapter.TodoViewHolder>() {
    private var dataList: Map<String, List<TodoEntity>> = mapOf()
    private val todoListAdapter by lazy { TodoListAdapter() }
    private var listener: OnPassStateInterface? = null

    init {
        setHasStableIds(true)
    }

    interface OnPassStateInterface {
        fun passValue(position: Int)
    }

    fun setOnPassStateInterface(listener: OnPassStateInterface) {
        this.listener = listener
    }

    inner class TodoViewHolder(private val binding: TodoDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentDate: Map<String, List<TodoEntity>>) {
            val result = dataList.values.toList().flatten()
            binding.todoDateTitle.text = currentDate.keys.first()
            todoListAdapter.setItem(result)
            todoListAdapter.setOnStateInterface(object : TodoListAdapter.OnSendStateInterface {
                override fun sendValue(position: Int) {
                    if (position != RecyclerView.NO_POSITION) {
                        listener?.passValue(position)
                    }
                }
            })
            binding.adapter = todoListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoDateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(dataList)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }


    fun setItem(data: List<TodoEntity>) {
        val mapData = mutableMapOf<String, List<TodoEntity>>()
        var getDateTitle = ""
        val resultData = mutableListOf<TodoEntity>()
        data.map {
            if (getDateTitle == "") {
                getDateTitle = convertTimestampToDate(it.createdAt)
            }

            if (getDateTitle == convertTimestampToDate(it.createdAt)) {
                resultData.add(it)
            }
            mapData.put(getDateTitle, resultData)
        }
        dataList = mapData
        notifyDataSetChanged()
    }
}