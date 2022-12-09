package com.msk.simpletodo.data.model.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    suspend fun createToDo(todoEntity: TodoEntity): Long
}