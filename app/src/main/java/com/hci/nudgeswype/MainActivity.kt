package com.hci.nudgeswype

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*


class MainActivity : Activity() {

    private var notificationManager: NotificationManager? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private fun storeInLocalStorage() {
        val Context = this.applicationContext
         val DestinationFile = Context.filesDir.path + File.separator + "reminders.json"
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

        var reminders = MainFragment.createReminderList(this.applicationContext)
        setContentView(R.layout.activity_main)



        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter(reminders)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }


            notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        addReminder.setOnClickListener{
            val intent = Intent(this, AddReminder::class.java)
            startActivity(intent)
        }
    }

    companion object {
        fun setAlarm(context: Context, requestCode: Int, nowSeconds: Long, secondsRemaining: Long) {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
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
    }
}
