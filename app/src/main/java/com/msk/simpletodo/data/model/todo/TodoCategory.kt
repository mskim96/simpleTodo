package com.msk.simpletodo.data.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_category_table")
data class TodoCategory(
    @PrimaryKey(autoGenerate = true)
    val tid: Long = 0,

    @ColumnInfo(name = "todo_category")
    val category: String,

    @ColumnInfo(name = "todo_category_icon")
    val categoryIcon: String,


    ) {
    companion object {
        private const val PERSONAL_ID = 1L
        private const val WORK_ID = 2L
        private const val STUDY_ID = 3L
        private const val OTHER_ID = 4L

        val PERSONAL = TodoCategory(tid = PERSONAL_ID, category = "Personal", "todo_user_icon")
        val WORK = TodoCategory(tid = WORK_ID, category = "Work", "todo_work_icon")
        val STUDY = TodoCategory(tid = STUDY_ID, category = "Study", "todo_study_icon")
        val OTHER = TodoCategory(tid = OTHER_ID, category = "Other", "todo_other_icon")
    }
}