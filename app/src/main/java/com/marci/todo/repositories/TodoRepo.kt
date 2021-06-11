package com.marci.todo.repositories

import com.marci.todo.db.TodoDatabase
import com.marci.todo.models.Todo

class TodoRepo(private val db: TodoDatabase) {

    suspend fun upsertTodo(todo: Todo) = db.todoDao.upsertTodo(todo)

    suspend fun deleteTodo(todo: Todo) = db.todoDao.deleteTodo(todo)

    fun getAllTodo() = db.todoDao.getAllTodos()
}