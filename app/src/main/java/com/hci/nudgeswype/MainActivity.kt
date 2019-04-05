package com.hci.nudgeswype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : SingleFragmentActivity() {
    override fun createFragment() = MainFragment.newInstance()


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







    var str = "0"
    val fragManager = supportFragmentManager
    val fragTransaction = fragManager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        storeInLocalStorage()

        super.onCreate(savedInstanceState)
        //if (savedInstanceState!!.getBoolean("AddFragment")) {
           // add(reminderFragment())
        //}
        setContentView(R.layout.activity_main)
        //val fragManager = supportFragmentManager
        //val fragTransaction = fragManager.beginTransaction()

        addReminder.setOnClickListener{
            val intent = Intent(this, AddReminder::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        //var list = intent.extras.get("reminderInfo")

    }
    fun add(reminderFrag: reminderFragment) {
        fragTransaction.add(reminderFrag, str)
        fragTransaction.commit()
        str += "0"
    }



}
