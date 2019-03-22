package com.hci.nudgeswype

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var str = "0"
    val fragManager = supportFragmentManager
    val fragTransaction = fragManager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if (savedInstanceState!!.getBoolean("AddFragment")) {
           // add(reminderFragment())
        //}
        setContentView(R.layout.activity_main)
        //val fragManager = supportFragmentManager
        //val fragTransaction = fragManager.beginTransaction()

        addReminder.setOnClickListener{
            val intent = Intent(this, AddReminder::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        add(reminderFragment())
    }
    fun add(reminderFrag: reminderFragment) {
        fragTransaction.add(reminderFrag, str)
        fragTransaction.commit()
        str += "0"
    }



}
