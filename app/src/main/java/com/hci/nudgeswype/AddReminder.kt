package com.hci.nudgeswype

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_reminder.*

class AddReminder : AppCompatActivity() {

    /*var hour : Spinner? = null
    var minutes: Spinner? = null
    var period: Spinner? = null
    var hour_times = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
    var minute_times = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,
        32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59)
    var periods = arrayOf("AM", "PM")*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        /*
        * Set time for Reminder
        * Beginning
        */
        // Hour spinner
        // Create an ArrayAdapter
        val hour_adapter = ArrayAdapter.createFromResource(
            this,
            R.array.hour_times, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        hour_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        hour_spinner.adapter = hour_adapter

        //Minute spinner
        // Create an ArrayAdapter
        val minute_adapter = ArrayAdapter.createFromResource(
            this,
            R.array.minute_times, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        minute_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        minute_spinner.adapter = minute_adapter

        //Period spinner
        // Create an ArrayAdapter
        val period_adapter = ArrayAdapter.createFromResource(
            this,
            R.array.periods, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        period_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        period_spinner.adapter = period_adapter

        /*
        * Set time for Reminder
        * Fin
        */

        /*
        * Create name for Reminder
        * Beginning
        */

        /*
        * Create name for Reminder
        * Fin
        */

        /*
        * Set snooze time for Reminder
        * Beginning
        */
        // Hour spinner
        // Create an ArrayAdapter
        val hour_snooze_adapter = ArrayAdapter.createFromResource(
            this,
            R.array.hour_times, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        hour_snooze_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        hour_snooze_spinner.adapter = hour_snooze_adapter

        //Minute spinner
        // Create an ArrayAdapter
        val minute_snooze_adapter = ArrayAdapter.createFromResource(
            this,
            R.array.minute_times, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        minute_snooze_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        minute_snooze_spinner.adapter = minute_snooze_adapter
        /*
        * Set snooze time for Reminder
        * Fin
        */

        // create new reminderTime picker

      // reminderTime().show(supportFragmentManager,"reminderTimePicker");

        button_create.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button_cancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    fun getValues(view: View) {
        Toast.makeText(
            this, "hour spinner  " + hour_spinner.selectedItem.toString() +
                    "\nminute spinner " + minute_spinner.selectedItem.toString() + "\nperiod spinner " +
                    period_spinner.selectedItem.toString(), Toast.LENGTH_LONG
        ).show()
    }
}