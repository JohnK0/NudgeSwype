package com.hci.nudgeswype

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime
import java.util.*

class AlarmUtil(){
    companion object {
        fun setAlarm(context: Context, requestCode: Int, nowSeconds: Long, alarmTime: Long, snoozeTime: Long, isSnooze: Boolean, reminderName: String) {
            var wakeUpTime: Long


            if (!isSnooze) {
                var alarmTime_ = alarmTime
                val calendar = Calendar.getInstance()

                val cur_per = calendar.get(Calendar.AM_PM)
                val cur_hour = calendar.get(Calendar.HOUR)
                val cur_min = calendar.get(Calendar.MINUTE)
                val cur_sec = calendar.get(Calendar.SECOND)

                var cur_time = cur_hour * 3600 + cur_min * 60 + cur_sec
                if (cur_per == 1) cur_time += 43200
                if (alarmTime_ < cur_time) alarmTime_ += 86400
                alarmTime_ -= cur_time
                wakeUpTime = alarmTime_ + nowSeconds
            }

            else
                wakeUpTime = nowSeconds + snoozeTime

            wakeUpTime = wakeUpTime * 1000

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderExpiredReceiver::class.java)
            intent.putExtra("request code", requestCode)
            intent.putExtra("reminder snooze",snoozeTime)
            intent.putExtra("reminder alarm",alarmTime)
            intent.putExtra("reminder name",reminderName)
            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
        }

        val nowSeconds: Long
            get() = System.currentTimeMillis() / 1000

        fun removeAlarm(context: Context){
            val intent = Intent(context, ReminderExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }
}