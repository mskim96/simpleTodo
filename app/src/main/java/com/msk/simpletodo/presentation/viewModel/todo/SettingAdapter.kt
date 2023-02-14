package com.msk.simpletodo.presentation.viewModel.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.databinding.SettingListItemBinding

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    private val settingList = listOf(
        "Account",
        "Notification",
        "Privacy",
        "Theme",
        "General",
        "About us",
        "Language",
        "More"
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SettingViewHolder(SettingListItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return settingList.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(settingList[position])
    }

    inner class SettingViewHolder(private val binding: SettingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(settingItem: String) {
            binding.settingMenu.text = settingItem
        }
    }
}