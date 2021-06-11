package com.marci.todo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.marci.todo.models.Todo

@Dao
interface TodoDao {

    // Insert and Update (can work as update too, because of the OnConflictStrategy)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todos")
    fun getAllTodos(): LiveData<List<Todo>>
}