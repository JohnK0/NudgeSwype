package com.hci.nudgeswype

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ReminderNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_SNOOZE -> {
      //          val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = MainActivity.setAlarm(context, MainActivity.nowSeconds, 5)
                //PrefUtil.setTimerState(MainActivity.TimerState.Running, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_FINISH -> {
                MainActivity.removeAlarm(context)
//                PrefUtil.setTimerState(MainActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
        }
    }
}