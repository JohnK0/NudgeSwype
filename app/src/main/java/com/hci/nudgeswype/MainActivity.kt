package com.hci.nudgeswype

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : SingleFragmentActivity() {

    override fun createFragment() = MainFragment.newInstance()
    private var notificationManager: NotificationManager? = null


    private fun storeInLocalStorage() {
        val Context = applicationContext
         val DestinationFile = Context.filesDir.path + File.separator + "reminders.json"
        //val DestinationFile = baseContext.getFileStreamPath("reminders.json")
        if (!File(DestinationFile).exists()) {
            try {
                CopyFromAssetsToStorage(Context, "reminders.json", DestinationFile.toString())
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
    }

    @Throws(IOException::class)
    private fun CopyFromAssetsToStorage(Context: Context, SourceFile: String, DestinationFile: String) {
        val IS = Context.getAssets().open(SourceFile)
        val internalJSON = File(DestinationFile)
        internalJSON.createNewFile()
        val OS = FileOutputStream(internalJSON)
        CopyStream(IS, OS)
        OS.flush()
        OS.close()
        IS.close()
    }

    @Throws(IOException::class)
    private fun CopyStream(Input: InputStream, Output: OutputStream) {
        val buffer = ByteArray(5120)
        var length = Input.read(buffer)
        while (length > 0) {
            Output.write(buffer, 0, length)
            length = Input.read(buffer)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        storeInLocalStorage()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
            "com.hci.nudgeswype",
            "reminder notification",
            "reminder notification example"
        )

        sendNotification(notification_button)

        addReminder.setOnClickListener{
            val intent = Intent(this, AddReminder::class.java)
            startActivity(intent)
        }
    }


    private fun createNotificationChannel(id: String, name: String, description: String){
        //  importance level set to low
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }


    fun sendNotification(view: View) {

        val notificationID = 101

        val mainIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelID = "com.hci.nudgeswype"

        val icon: Icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        val action: Notification.Action =
                Notification.Action.Builder(icon, "Open", pendingIntent).build()
        val notification = Notification.Builder(this@MainActivity,
            channelID)
            .setContentTitle("Reminder Notification")
            .setContentText("This is an example notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channelID)
            .setContentIntent(pendingIntent)
            .setActions(action)
            .build()

        notificationManager?.notify(notificationID, notification)

    }





}
