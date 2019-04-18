package com.hci.nudgeswype

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
//https://github.com/ResoCoder/TimerAppAndroidTutorial/tree/master/app/src/main/java/com/resocoder/timertutorial
class ReminderExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context,intent)
    }
}