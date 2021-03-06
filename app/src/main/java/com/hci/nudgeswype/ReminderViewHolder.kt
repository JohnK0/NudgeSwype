package com.hci.nudgeswype

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
    private var reminderBox: ConstraintLayout? = null
    private var deleteBox: CheckBox? = null

    override fun onClick(v: View?) {
        val viewID = v!!.id


        if (viewID.equals(R.id.reminderSwitch)) {
            updateJSON(this.adapterPosition, parentContext)
        }
    }

    companion object {
        fun updateJSONFromNotification(adapterPosition: Int, parentContext: Context) {
            val file = loadJSON(parentContext )

            val reminders = JSONObject(file).getJSONObject("reminders")

            //val length = totalReminders.length()


            val currentReminder = reminders.getJSONObject((adapterPosition+1).toString())
            var currentVal = currentReminder.getBoolean("is_active")


            currentReminder.remove(("is_active"))
            currentReminder.put("is_active",!currentVal)

            reminders.remove((adapterPosition+1).toString())
            reminders.put((adapterPosition+1).toString(),currentReminder)

            writeToFile(reminders, parentContext)
            //reminders.remove((adapter))
        }

        fun updateJSON(adapterPosition: Int, parentContext: Context) {
            val file = loadJSON(parentContext )

            val reminders = JSONObject(file).getJSONObject("reminders")

            //val length = totalReminders.length()


            val currentReminder = reminders.getJSONObject((adapterPosition+1).toString())
            var currentVal = currentReminder.getBoolean("is_active")

            currentReminder.remove(("is_active"))
            currentReminder.put("is_active",!currentVal)

            reminders.remove((adapterPosition+1).toString())
            reminders.put((adapterPosition+1).toString(),currentReminder)

            writeToFile(reminders, parentContext)
            //reminders.remove((adapter))

        }
        fun writeToFile(reminders: JSONObject, parentContext: Context) {
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
    }

    init {
        reminderNameView = itemView.findViewById(R.id.reminderName)
        reminderTimeView = itemView.findViewById(R.id.reminderTime)
        reminderSnoozeView = itemView.findViewById(R.id.reminderSnooze)
        reminderSwitch = itemView.findViewById(R.id.reminderSwitch)
        reminderBox = itemView.findViewById(R.id.reminderBox)
        deleteBox = itemView.findViewById(R.id.deleteBox)

    }

    fun bind(reminder: reminder_object) {
        reminderNameView?.text = reminder.name
        reminderTimeView?.text = reminder.reminder_time
        reminderSnoozeView?.text = reminder.snooze_time
        reminderSwitch?.setChecked(reminder.isChecked)

        changeBackground(reminderSwitch!!.isChecked,reminderBox)
        (this.adapterPosition)
        reminderSwitch?.setOnCheckedChangeListener({ _, isChecked ->
            updateJSON(this.adapterPosition, parentContext)
            changeBackground(isChecked,reminderBox)
            alarmState(isChecked, this.adapterPosition, reminder)

        })
    }

    fun timeToSeconds(reminder: reminder_object): Long {
        val slicedReminder = reminder.reminder_time.removePrefix("Reminder Time: ")
        var reminderHour: Int
        val reminderMin: Int
        val amOrPm = slicedReminder.substring(slicedReminder.length-2)

        var index = 0
        var reminderHourString: String = ""
        while (slicedReminder.get(index) != ':') {
            reminderHourString += slicedReminder.get(index)
            index++;
        }

        reminderHour = reminderHourString.toInt()

        reminderMin = slicedReminder.substring(index+1,index+3).toInt()
        if (amOrPm.equals("PM")) {
            if (reminderHour < 12) {
                reminderHour += 12
            }
        }
        Log.i("hour",reminderHour.toString())
        Log.i("min",reminderMin.toString())
        Log.i("amorpm",amOrPm)

        return (reminderHour*3600 + reminderMin*60).toLong()
    }

    // converts snooze string into a string
    fun convertSnoozeToSeconds(snoozeStr: String): Int {
        val trimStr = snoozeStr.removePrefix("Snooze:\n");

        val snoozeMin = trimStr.toInt()

        val snoozeSeconds = snoozeMin * 60

        return snoozeSeconds
    }

    private fun alarmState(checked: Boolean, adapterPosition: Int, reminder: reminder_object) {
        if (checked) {
            val alarmTime = timeToSeconds(reminder)
            val snoozeTime = convertSnoozeToSeconds(reminder.snooze_time).toLong()
            Log.d("ALARM TIME: ", alarmTime.toString())
            Log.d("SNOOZE TIME: ",  snoozeTime.toString())
            AlarmUtil.setAlarm(parentContext, adapterPosition, AlarmUtil.nowSeconds, alarmTime, snoozeTime, false, reminder.name)
        }
    }

    private fun changeBackground(checked: Boolean, view: ConstraintLayout?) {
        if (checked) {
            view!!.setBackgroundColor(Color.rgb(51, 181, 229))
        } else {

            view!!.setBackgroundColor(Color.LTGRAY)
        }
    }

    public fun deleteAlarm() {
        AlarmUtil.removeAlarm(parentContext)
    }

}