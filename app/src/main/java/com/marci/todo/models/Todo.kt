package com.marci.todo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var title: String,
    var checked: Boolean
)
