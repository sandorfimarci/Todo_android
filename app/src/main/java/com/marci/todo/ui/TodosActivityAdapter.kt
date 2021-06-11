package com.marci.todo.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.marci.todo.R
import com.marci.todo.models.Todo

class TodosActivityAdapter : RecyclerView.Adapter<TodosActivityAdapter.VH>() {

    var deleteClick: ((Todo) -> Unit)? = null
    var checkTodo: ((Todo) -> Unit)? = null
    var itemClick: ((Todo) -> Unit)? = null

    private var todosList: MutableList<Todo> = emptyList<Todo>().toMutableList()

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: AppCompatTextView = view.findViewById(R.id.title)
        val checkbox: AppCompatCheckBox = view.findViewById(R.id.checkbox)
        val deleteBtn: Button = view.findViewById(R.id.btn_delete)
        val conatiner: ConstraintLayout = view.findViewById(R.id.container_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = todosList[position]

        holder.title.text = item.title
        holder.checkbox.isChecked = item.checked
        setChecked(holder)

        holder.deleteBtn.setOnClickListener {
            deleteClick?.invoke(item)
        }

        holder.checkbox.setOnClickListener {
            item.checked = holder.checkbox.isChecked
            checkTodo?.invoke(item)

            setChecked(holder)
        }

        holder.conatiner.setOnClickListener {
            itemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = todosList.size

    fun setData(list: MutableList<Todo>) {
        todosList = list
        notifyDataSetChanged()
    }

    private fun setChecked(holder : VH){
        if (holder.checkbox.isChecked)
            holder.title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        else
            holder.title.paintFlags = 0
    }

}