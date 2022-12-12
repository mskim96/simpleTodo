package com.msk.simpletodo.data.model.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun createToDo(todoEntity: TodoEntity)
}