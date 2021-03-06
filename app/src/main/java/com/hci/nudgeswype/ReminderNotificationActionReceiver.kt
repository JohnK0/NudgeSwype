package com.hci.nudgeswype

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

//https://github.com/ResoCoder/TimerAppAndroidTutorial/tree/master/app/src/main/java/com/resocoder/timertutorial
class ReminderNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_SNOOZE -> {
      //          val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                AlarmUtil.setAlarm(context, intent.getIntExtra("request code", 0), AlarmUtil.nowSeconds, 5,
                    intent.getLongExtra("reminder snooze time", 0), true, intent.getStringExtra("reminder name"))
                //PrefUtil.setTimerState(MainActivity.TimerState.Running, context)
                NotificationUtil.hideTimerNotification(context)

            }
            AppConstants.ACTION_FINISH -> {
                AlarmUtil.removeAlarm(context)
               // ReminderViewHolder.updateJSON(intent.getIntExtra("request code", 0), context)
//                PrefUtil.setTimerState(MainActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
        }
    }
}