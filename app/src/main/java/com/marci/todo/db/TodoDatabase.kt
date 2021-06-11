package com.marci.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marci.todo.models.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    // Create the actual database (but only one instance)
    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo_db.db"
            ).build()
    }
}