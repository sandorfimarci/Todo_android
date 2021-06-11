package com.marci.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marci.todo.models.Todo
import com.marci.todo.repositories.TodoRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TodoRepo) : ViewModel() {

    fun getAllTodo(): LiveData<List<Todo>> = repository.getAllTodo()

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        repository.deleteTodo(todo)
    }

    fun upsertTodo(todo: Todo) = viewModelScope.launch {
        repository.upsertTodo(todo)
    }
}