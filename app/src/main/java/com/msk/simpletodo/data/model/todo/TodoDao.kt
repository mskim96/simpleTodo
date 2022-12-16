package com.msk.simpletodo.data.model.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun createToDo(todoEntity: TodoEntity)

    @Update
    suspend fun checkTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)
}