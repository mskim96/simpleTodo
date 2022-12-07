package com.msk.simpletodo.presentation.viewModel.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.msk.simpletodo.R

fun Spinner.setCustomAdapter(
    context: Context,
    lst: MutableList<String>,
    iconList: MutableList<Int>,
    unselectedTitle: String = ""
) {
    class CustomSpnAdapter : BaseAdapter {
        var lst: MutableList<String> = mutableListOf<String>()
        var iconList: MutableList<Int> = mutableListOf<Int>()
        var context: Context
        var unselectedTitle: String

        constructor(
            context: Context,
            lst: MutableList<String>,
            iconList: MutableList<Int>,
            unselectedTitle: String
        ) {
            this.context = context
            this.lst = lst
            this.iconList = iconList
            this.unselectedTitle = unselectedTitle
        }

        override fun getCount(): Int {
            return lst.size
        }

        override fun getItem(position: Int): Any {
            return lst[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = LayoutInflater.from(context).inflate(R.layout.todo_spinner_item, null)
            v.findViewById<TextView>(R.id.todoTypeTitle)?.apply {
                text = lst[position]
            }
            v.findViewById<ImageView>(R.id.todoTypeImage)?.apply {
                this.setImageResource(iconList[position])
            }
            return v
        }
    }
    adapter = CustomSpnAdapter(context, lst, iconList, unselectedTitle)
    this.setSelection(-1)
}