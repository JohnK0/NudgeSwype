package com.hci.nudgeswype

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class reminderFragment : Fragment() {

    // array of booleans that represent days of the week
   private var daysOfTheWeek: BooleanArray? = null
    private var daysOfTheWeekStr = arrayOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday")

     fun setData(savedInstanceState: Bundle?) {

        daysOfTheWeek = booleanArrayOf(false,false,false,false,false,false,false)

        for (day in daysOfTheWeekStr) {
            if (savedInstanceState!!.getBoolean(day)) {
                setDayOfTheWeek(day, true)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.reminder,container,false)
       // daysOfTheWeek = booleanArrayOf(false,false,false,false,false,false,false)
/*        val onSwitch =  view.findViewById<Switch>(R.id.toggleSwitch)
        onSwitch.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                view.setBackgroundColor(Color.rgb(51, 181, 229))
            } else {

                view.setBackgroundColor(Color.LTGRAY)
            }
        })*/
        return view
    }

    fun setDayOfTheWeek(day: String, flag: Boolean) {
        var index = 0
        for (dayName in daysOfTheWeekStr) {
            if (dayName.equals(day)) {

                daysOfTheWeek?.set(index, flag);
            }
            index++
        }

    }

    /*
    fun setDayOfTheWeekFalse(day: Int) {
        daysOfTheWeek?.set(day, false)
    } */

}
