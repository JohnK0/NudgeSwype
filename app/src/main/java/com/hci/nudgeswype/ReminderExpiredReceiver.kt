package com.hci.nudgeswype

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)
    }
}