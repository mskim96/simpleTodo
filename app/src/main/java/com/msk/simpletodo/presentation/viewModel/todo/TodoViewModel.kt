package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.TodoEntity
import com.msk.simpletodo.domain.todo.usecase.GetUserWithTodoUseCase
import com.msk.simpletodo.domain.todo.usecase.TodoCreateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val getUserWithTodoUseCase: GetUserWithTodoUseCase,
    private val preferencesDataStore: DataStore<Preferences>
) :
    ViewModel() {

    private val _todoData = MutableLiveData<List<TodoEntity>>()
    val todoData: LiveData<List<TodoEntity>> get() = _todoData

    init {
        getUserWithTodo(1)
    }

    val userIdFlow: Flow<Long?> = preferencesDataStore.data.map {
        val USER_ID = longPreferencesKey("user_id")
        it[USER_ID]
    }

    fun getUserWithTodo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _todoData.postValue(getUserWithTodoUseCase.execute(id))
        }
    }


    fun createTodo(title: String, todoType: String, utid: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (todoType) {
                "Person" -> todoCreateUseCase.execute(
                    TodoEntity(
                        title = title,
                        todoType = todoType,
                        utid = utid
                    )
                )
                "Work" -> todoCreateUseCase.execute(
                    TodoEntity(
                        title = title,
                        todoType = todoType,
                        utid = utid
                    )
                )
                "Study" -> todoCreateUseCase.execute(
                    TodoEntity(
                        title = title,
                        todoType = todoType,
                        utid = utid
                    )
                )
            }
        }
    }
}