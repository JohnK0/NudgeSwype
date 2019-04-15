package com.hci.nudgeswype

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.view.View


class NotificationUtil {
    companion object {
        val channelID = "com.hci.nudgeswype"
        private const val channelName = "reminder notification"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context){
            val snoozeIntent = Intent(context, ReminderNotificationActionReceiver::class.java)
            snoozeIntent.action = AppConstants.ACTION_SNOOZE
            val snoozePendingIntent = PendingIntent.getBroadcast(context,
                0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val finishIntent = Intent(context, ReminderNotificationActionReceiver::class.java)
            finishIntent.action = AppConstants.ACTION_FINISH
            val finishPendingIntent = PendingIntent.getBroadcast(context,
                0, finishIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nBuilder = getBasicNotificationBuilder(context, channelID, true)
            nBuilder.setContentTitle("Reminder!")
                .setContentText("Finished?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.drawable.notification_template_icon_bg, "Snooze", snoozePendingIntent)
                .addAction(R.drawable.abc_seekbar_thumb_material, "Finish", finishPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(channelID, channelName, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelID: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW
                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }

        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder{
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.design_snackbar_background)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}
