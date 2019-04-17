package com.hci.nudgeswype

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_add_reminder.*
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class AddReminder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        val np: NumberPicker = findViewById(R.id.numPicker)

        np.setMinValue(1)
        np.setMaxValue(59)
        np.setWrapSelectorWheel(true)

        button_create.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            addReminderJSON(getReminderTime(), getSnoozeTime(), getReminderName())
            startActivity(intent)
        }
        button_cancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun convertMinuteToString(minute: Int): String {
        if (minute < 10)
            return "0" + minute.toString()
        return minute.toString()

    }

    fun getReminderTime(): String {
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        var (reminderHour, reminderMinute) = Pair(timePicker.getHour(), timePicker.getMinute())

        var period = "AM"
        if (reminderHour > 11) {
            period = "PM"
            if (reminderHour != 12)
                reminderHour = reminderHour - 12
        }

        val reminderTime = reminderHour.toString() + ":" + convertMinuteToString(reminderMinute) + " " + period
        return reminderTime

    }

    fun getSnoozeTime(): String {
        val snoozePicker: NumberPicker = findViewById(R.id.numPicker)
        val snoozeTime = snoozePicker.getValue()
        return "0:" + convertMinuteToString(snoozeTime)
    }

    fun getReminderName(): String {
        val ReminderTextView: TextView = findViewById(R.id.reminderName)
        val reminderName = ReminderTextView.getText().toString()

        return reminderName
    }

    // loads the contents of the JSON file  into a String
    fun loadJSON(): String? {
        var json: String? = null
        try {
            val `is` = FileInputStream( applicationContext.filesDir.path + File.separator + "reminders.json") //context!!.openFileInput("reminders.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    // gets the values of objects in view and stores them in the JSON file.
    fun addReminderJSON(reminder_time: String, snooze_time: String, name: String) {
        val fullJSON = JSONObject(loadJSON())
        val totalReminders = fullJSON.getJSONObject("reminders")
        val numReminders = totalReminders.length()
        var newReminder = JSONObject();
        newReminder.put("reminder_time",reminder_time)
        newReminder.put("snooze_time",snooze_time)
        newReminder.put("reminder_name",name)
        newReminder.put("is_active",false);

        totalReminders.put((numReminders + 1).toString(),newReminder)

        val newJsonText = "{ \"reminders\":" + totalReminders.toString() + "}"
        Log.i("newjson",newJsonText)

        try {
            val `is` = FileOutputStream( applicationContext.filesDir.path + File.separator + "reminders.json")
            `is`.write(newJsonText.toByteArray())
            `is`.close()
        } catch (ex: IOException) {
            ex.printStackTrace()

        }
    }
}