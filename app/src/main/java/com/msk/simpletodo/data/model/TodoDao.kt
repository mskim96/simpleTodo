package com.msk.simpletodo.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    suspend fun createToDo(todoEntity: TodoEntity): Long

    @Query("SELECT * FROM todo_list WHERE fk_utid =:id")
    fun getUserWithTodo(id: Long): List<TodoEntity>
}