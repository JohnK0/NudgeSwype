package com.hci.nudgeswype

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hci.nudgeswype.ReminderViewHolder

class ListAdapter(private val list: List<reminder_object>)
    : RecyclerView.Adapter<ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReminderViewHolder(inflater, parent,parent.context)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder: reminder_object = list[position]
        holder.bind(reminder)
    }

    override fun getItemCount(): Int = list.size

}

