package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.example.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val notificationId = 45
    private val channelID = "com.example.notificationdemo.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "DemoChannel", "this is a demo")

        binding.button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {

        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            FLAG_MUTABLE)

        // Reply action
        val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here")
            build()
        }
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent
        ).addRemoteInput(remoteInput).build()

        // Add button action to navigate to details activity
        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2 = PendingIntent.getActivity(
            this,
            0,
            intent2,
            FLAG_MUTABLE)
        val action2 = NotificationCompat.Action.Builder(0, "Details", pendingIntent2).build()

        // Add button action to navigate to settings activity
        val intent3 = Intent(this, SettingsActivity::class.java)
        val pendingIntent3 = PendingIntent.getActivity(
            this,
            0,
            intent3,
            FLAG_MUTABLE)
        val action3 = NotificationCompat.Action.Builder(0, "Settings", pendingIntent3).build()

        val notification =
            NotificationCompat.Builder(this@MainActivity, channelID).setContentTitle("Demo Title")
                .setContentText("This is a demo notification")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(action2)
                .addAction(action3)
                .addAction(replyAction)
                .build()
        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}