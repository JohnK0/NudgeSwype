package com.hci.nudgeswype

import android.graphics.Movie
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter(private val list: List<reminder_object>)
    : RecyclerView.Adapter<ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReminderViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder: reminder_object = list[position]
        holder.bind(reminder)
    }

    override fun getItemCount(): Int = list.size

}

class ReminderViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.reminder, parent, false)) {
    private var reminderNameView: TextView? = null
    private var reminderTimeView: TextView? = null
    private var reminderSnoozeView: TextView? = null


    init {
        reminderNameView = itemView.findViewById(R.id.reminderName)
        reminderTimeView = itemView.findViewById(R.id.reminderTime)
        reminderSnoozeView = itemView.findViewById(R.id.reminderSnooze)
    }

    fun bind(reminder: reminder_object) {
        reminderNameView?.text = reminder.name
        reminderTimeView?.text = reminder.reminder_time
        reminderSnoozeView?.text = reminder.snooze_time
    }

}