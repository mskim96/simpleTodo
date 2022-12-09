package com.msk.simpletodo.data.model.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoCategoryDao {

    @Insert
    fun createCategory(todoCategory: TodoCategory)

    @Transaction
    @Query("SELECT * FROM todo_category_table")
    fun getCategoryWithTodo(): List<TodoCategoryWithTodo>
}