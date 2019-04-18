package com.hci.nudgeswype

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmUtil(){
    companion object {
        fun setAlarm(context: Context, requestCode: Int, nowSeconds: Long, alarmTime: Long, isSnooze: Boolean) {
            var wakeUpTime: Long
            wakeUpTime = alarmTime
            if (isSnooze)
                wakeUpTime = (nowSeconds + wakeUpTime)
            wakeUpTime = wakeUpTime * 1000

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderExpiredReceiver::class.java)
            intent.putExtra("request code", requestCode)
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


        // converts snooze string into a string
        fun convertSnoozeToSeconds(snoozeStr: String): Int {
            val trimStr = snoozeStr.removePrefix("Snooze\n");

            val snoozeMin = trimStr.toInt()

            val snoozeSeconds = snoozeMin * 60

            return snoozeSeconds
        }
    }
}