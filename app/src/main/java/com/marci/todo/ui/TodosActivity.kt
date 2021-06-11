package com.marci.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.marci.todo.R
import com.marci.todo.databinding.ActivityTodosBinding
import com.marci.todo.databinding.DialogAddBinding
import com.marci.todo.db.TodoDatabase
import com.marci.todo.models.Todo
import com.marci.todo.repositories.TodoRepo

class TodosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodosBinding

    private lateinit var viewModel: MainViewModel
    private var todosAdapter: TodosActivityAdapter = TodosActivityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up viewbinding
        binding = ActivityTodosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        addTodo()
        updateUI()
        setUpClicks()
    }

    private fun setUpViewModel() {
        val repository = TodoRepo(TodoDatabase(this))
        viewModel = MainViewModel(repository)
    }

    private fun addTodo() {
        binding.fabAdd.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog(text: String = "") {
        val dialogBinding = DialogAddBinding.inflate(LayoutInflater.from(this))
        val dialog = BottomSheetDialog(this, R.style.DialogStyle)
            .apply {
                setContentView(dialogBinding.root)
                show()
            }

        dialogBinding.btnSave.setOnClickListener {
            viewModel.upsertTodo(
                Todo(title = dialogBinding.addTitle.text.toString(), checked = false)
            )
            Snackbar.make(
                findViewById(R.id.constraintlayout),
                "Todo saved.",
                Snackbar.LENGTH_SHORT
            ).show()

            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Show to-do's text, if exist
        // To display a to-do details
        if (text.isNotEmpty()) {
            dialogBinding.addTitle.text?.append(text)
        }
    }

    private fun updateUI() {
        binding.recyclerviewTodo.apply {
            adapter = todosAdapter
            layoutManager = LinearLayoutManager(context)
        }

        refreshTodoList()
    }

    private fun refreshTodoList() {
        viewModel.getAllTodo().observe(this, Observer { todos ->
            todos?.let { todoList ->
                todosAdapter.setData(todoList as MutableList<Todo>)
            }
        })
    }

    private fun setUpClicks() {
        todosAdapter.deleteClick = {
            viewModel.deleteTodo(it)
        }

        todosAdapter.checkTodo = {
            viewModel.upsertTodo(it)
        }

        todosAdapter.itemClick = {
            showBottomSheetDialog(it.title)
        }
    }
}