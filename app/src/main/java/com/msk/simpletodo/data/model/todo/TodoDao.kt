package com.msk.simpletodo.data.model.todo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun createToDo(todoEntity: TodoEntity): Long

    @Update
    suspend fun checkTodo(todoEntity: TodoEntity)

    @Update
    suspend fun editTodo(todoEntity: TodoEntity): Int

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity): Int

    @Query("SELECT * FROM todo_table WHERE todo_date Like :date")
    suspend fun getTaskByDate(date: String): List<TodoEntity>

    @Query("SELECT * FROM todo_table WHERE todo_title LIKE :query")
    fun getTaskByQuery(query: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo_table ORDER BY todo_updated DESC LIMIT 5")
    fun getTaskByRecent(): Flow<List<TodoEntity>>
}