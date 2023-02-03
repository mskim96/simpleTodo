package com.msk.simpletodo.presentation.viewModel.todo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.TodoCalendarItemBinding
import com.msk.simpletodo.domain.model.CalendarDate
import java.time.LocalDate

class CalendarAdapter(val context: Context, private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var dataSet = arrayListOf<CalendarDate>()
    var selectedPosition = LocalDate.now().dayOfMonth - 1

    inner class CalendarViewHolder(val binding: TodoCalendarItemBinding) :
        ViewHolder(binding.root) {
        fun bind(data: CalendarDate) {
            Log.d("TAG", "bind: $selectedPosition")
            if (selectedPosition == adapterPosition) {
                dataSet[adapterPosition].isSelected = true
                binding.setChecked()
            } else {
                dataSet[adapterPosition].isSelected = false
                binding.setUnChecked()
            }
            binding.apply {
                calendarDate.text = data.date
                calendarDay.text = data.day
                itemView.setOnClickListener {
                    binding.setChecked()
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    onClick(adapterPosition + 1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
        return CalendarViewHolder(TodoCalendarItemBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = dataSet.size

    fun setItem(data: ArrayList<CalendarDate>) {
        dataSet = data
        notifyDataSetChanged()
    }

    private fun TodoCalendarItemBinding.setChecked() {
        calendarDate.setTextColor(ContextCompat.getColor(context, R.color.font_case))
        calendarItemLayout.background =
            ContextCompat.getDrawable(context, R.drawable.todo_calendar_main)
    }

    private fun TodoCalendarItemBinding.setUnChecked() {
        calendarDate.setTextColor(ContextCompat.getColor(context, R.color.placeholder))
        calendarItemLayout.background = null
    }
}