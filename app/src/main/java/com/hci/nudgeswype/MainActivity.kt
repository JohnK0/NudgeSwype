package com.hci.nudgeswype

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.reminder.view.*
import java.io.*


class MainActivity : Activity() {

    private var notificationManager: NotificationManager? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var reminderViewGroup: ViewGroup? = null
    private var reminders: MutableList<reminder_object>? = null

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

         reminders = MainFragment.createReminderList(this.applicationContext)
        setContentView(R.layout.activity_main)



        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter(reminders as ArrayList<reminder_object>)




        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        reminderViewGroup = (viewAdapter as ListAdapter).getViewGroup()
            notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        addReminder.setOnClickListener{
            val intent = Intent(this, AddReminder::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            delete()
        }
    }

    fun delete() {
        var numDeletions = 0;
        var listToRemove = ArrayList<Int>()
        for (i in 0 until reminders!!.size) {
            if (recyclerView.getChildAt(i).deleteBox.isChecked) {
                (recyclerView.getChildViewHolder(recyclerView.getChildAt(i)) as ReminderViewHolder).deleteAlarm()
                MainFragment.deleteJSON((i+1)-numDeletions,this.applicationContext)
                //reminders!!.removeAt(i)
                listToRemove.add(i)
                //viewAdapter.notifyDataSetChanged()
                numDeletions++;
            }


        }
        var numDeletionsTwo = 0;
        for (j in 0 until listToRemove.size) {
            reminders!!.removeAt(listToRemove.get(j)-numDeletionsTwo)
            numDeletionsTwo++;
        }
        //reminders!!.removeAll(listToRemove)
        viewAdapter.notifyDataSetChanged()
    }


}
