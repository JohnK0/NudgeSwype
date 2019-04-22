package com.hci.nudgeswype

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListAdapter(private val list: MutableList<reminder_object>)
    : RecyclerView.Adapter<ReminderViewHolder>() {

    private var viewGroup: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        viewGroup = parent

        return ReminderViewHolder(inflater, parent,parent.context)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder: reminder_object = list[position]
        holder.bind(reminder)
    }

    override fun getItemCount(): Int = list.size

    fun getViewGroup(): ViewGroup? = this!!.viewGroup


}

