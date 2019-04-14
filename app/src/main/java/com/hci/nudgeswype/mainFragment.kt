package com.hci.nudgeswype

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_frag.*
import org.json.JSONObject
import java.io.IOException

data class reminder_object(val reminder_time: String, val snooze_time: String, val name: String, val isChecked: Boolean )

class MainFragment : Fragment() {

    private var reminders = ArrayList<reminder_object>(0)
    //reminders.add(reminder_object("4:30 AM", "0:40", "homework 1"))
    //   reminder_object("5:30 AM", "0:45", "homework 2"),
     //  reminder_object("6:30 AM", "0:50", "homework 3")


    fun getReminderList(): ArrayList<reminder_object> {
        return reminders
    }

    fun addToReminderList(reminder_object: reminder_object) {
        reminders.add(reminder_object)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //reminders.add(reminder_object("5:30 AM", "0:45", "homework 2"))
        //reminders.add(reminder_object("4:30 AM", "0:40", "homework 1"))
        //reminders.add(reminder_object("6:30 AM", "0:50", "homework 3"))

        parseJSON();
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.recycler_view_frag, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(reminders)
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    fun loadJSONFromAsset(): String? {
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

    private fun parseJSON() {
        val fullJSON = JSONObject(loadJSONFromAsset())
        val totalReminders = fullJSON.getJSONObject("reminders")

        val length = totalReminders.length()

        for (i in 0 until length) {
            val currentReminder = totalReminders.getJSONObject((i+1).toString())
            reminders.add(reminder_object(currentReminder.getString("reminder_time"), currentReminder.getString("snooze_time"),
                currentReminder.getString("reminder_name"),currentReminder.getBoolean("is_active")))

        }


    }
}