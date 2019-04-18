package com.hci.nudgeswype

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmUtil(){
    companion object {
        fun setAlarm(context: Context, requestCode: Int, nowSeconds: Long, alarmTime: Long, snoozeTime: Long, isSnooze: Boolean,
                     reminderName: String) {
            var wakeUpTime: Long
            if (!isSnooze)
                wakeUpTime = alarmTime
            else
                wakeUpTime = (nowSeconds + snoozeTime)
            wakeUpTime = wakeUpTime * 1000

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderExpiredReceiver::class.java)
            intent.putExtra("request code", requestCode)
            intent.putExtra("reminder name",reminderName)
            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        fun removeAlarm(context: Context){
            val intent = Intent(context, ReminderExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }
}