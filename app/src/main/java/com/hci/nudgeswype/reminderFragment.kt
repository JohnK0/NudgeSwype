package com.hci.nudgeswype

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch

class reminderFragment : Fragment() {

    // array of booleans that represent days of the week
   private var daysOfTheWeek: BooleanArray? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reminder,container,false)
        daysOfTheWeek = booleanArrayOf(false,false,false,false,false,false,false)
        val onSwitch =  view.findViewById<Switch>(R.id.toggleSwitch)
        onSwitch.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                view.setBackgroundColor(Color.rgb(51, 181, 229))
            } else {

                view.setBackgroundColor(Color.LTGRAY)
            }
        })
        return view
    }

    fun setDayOfTheWeekTrue(day: Int) {
        daysOfTheWeek?.set(day, true)
    }

    fun setDayOfTheWeekFalse(day: Int) {
        daysOfTheWeek?.set(day, false)
    }

}
