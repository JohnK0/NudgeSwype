package com.hci.nudgeswype

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.IOException

data class reminder_object(val reminder_time: String, val snooze_time: String, val name: String, val isChecked: Boolean )

class MainFragment  {

    //private var reminders = ArrayList<reminder_object>(0)

    // static method in kotlin
    // call this method to return arraylist of fragments
    companion object {
        // loads the contents of the JSON file  into a String
        fun loadJSON(context: Context): String? {
            var json: String? = null
            try {
                val `is` = context!!.openFileInput("reminders.json") //FileInputStream("reminders.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = buffer.toString(Charsets.UTF_8)
            } catch (ex: IOException) {
                print("UNABLE TO OPEN FILE")
                ex.printStackTrace()
                return null
            }
            Log.i("jsonfile", "TRSTI")
            return json
        }

        // parses through the JSON to add reminder objects into the array list
        private fun parseJSON(context: Context): ArrayList<reminder_object> {
            var reminders = ArrayList<reminder_object>(0)
            val fullJSON = JSONObject(loadJSON(context))
            val totalReminders = fullJSON.getJSONObject("reminders")

            val length = totalReminders.length()

            for (i in 0 until length) {
                val currentReminder = totalReminders.getJSONObject((i+1).toString())
                reminders.add(reminder_object(currentReminder.getString("reminder_time"), currentReminder.getString("snooze_time"),
                    currentReminder.getString("reminder_name"),currentReminder.getBoolean("is_active")))

            }
            return reminders;



        }
        fun createReminderList(context: Context): ArrayList<reminder_object> {
            return parseJSON(context)
        }
    }


}