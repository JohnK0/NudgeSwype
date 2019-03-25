package com.hci.nudgeswype

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_frag.*

data class reminder_object(val reminder_time: String, val snooze_time: String, val name: String)

class MainFragment : Fragment() {

    private val reminders = ArrayList<reminder_object>(0)
    //reminders.add(reminder_object("4:30 AM", "0:40", "homework 1"))
    //   reminder_object("5:30 AM", "0:45", "homework 2"),
     //  reminder_object("6:30 AM", "0:50", "homework 3")



    override fun onCreate(savedInstanceState: Bundle?) {
        reminders.add(reminder_object("4:30 AM", "0:40", "homework 1"))
        reminders.add(reminder_object("5:30 AM", "0:45", "homework 2"))
        reminders.add(reminder_object("6:30 AM", "0:50", "homework 3"))
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
}