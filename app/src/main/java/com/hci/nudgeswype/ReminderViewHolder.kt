package com.hci.nudgeswype

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.hci.nudgeswype.MainFragment.Companion.loadJSON
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ReminderViewHolder(inflater: LayoutInflater, parent: ViewGroup, parentContext: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.reminder, parent, false)), View.OnClickListener  {

    val parentContext = parentContext

    private var reminderNameView: TextView? = null
    private var reminderTimeView: TextView? = null
    private var reminderSnoozeView: TextView? = null
    private val reminderSwitch: Switch?


    override fun onClick(v: View?) {
        val viewID = v!!.id


        if (viewID.equals(R.id.reminderSwitch)) {
            updateJSON(this.adapterPosition)
        }
    }

    private fun updateJSON(adapterPosition: Int) {
        val file = loadJSON(parentContext )

        val reminders = JSONObject(file).getJSONObject("reminders")

        //val length = totalReminders.length()


            val currentReminder = reminders.getJSONObject((adapterPosition+1).toString())
            var currentVal = currentReminder.getBoolean("is_active")

            currentReminder.remove(("is_active"))
            currentReminder.put("is_active",!currentVal)

            reminders.remove((adapterPosition+1).toString())
            reminders.put((adapterPosition+1).toString(),currentReminder)

            writeToFile(reminders)
            //reminders.remove((adapter))

    }

    private fun writeToFile(reminders: JSONObject) {
        val newJsonText = "{ \"reminders\":" + reminders.toString() + "}"
        //Log.i("newjson",newJsonText)

        try {
            val `is` = FileOutputStream( parentContext.filesDir.path + File.separator + "reminders.json")
            `is`.write(newJsonText.toByteArray())
            `is`.close()
        } catch (ex: IOException) {
            ex.printStackTrace()

        }
    }

    init {
        reminderNameView = itemView.findViewById(R.id.reminderName)
        reminderTimeView = itemView.findViewById(R.id.reminderTime)
        reminderSnoozeView = itemView.findViewById(R.id.reminderSnooze)
        reminderSwitch = itemView.findViewById(R.id.reminderSwitch)

    }

    fun bind(reminder: reminder_object) {
        reminderNameView?.text = reminder.name
        reminderTimeView?.text = reminder.reminder_time
        reminderSnoozeView?.text = reminder.snooze_time
        reminderSwitch?.setChecked(reminder.isChecked)
        reminderSwitch?.setOnCheckedChangeListener({ _, isChecked ->
            updateJSON(this.adapterPosition)
        })
    }

}