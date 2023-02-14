package com.msk.simpletodo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.msk.simpletodo.R
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class NotificationReceiver : BroadcastReceiver() {

    private lateinit var manager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    companion object {
        const val CHANNEL_ID = "channel"
        const val CHANNEL_NAME = "channel1"
    }

    override fun onReceive(context: Context, intent: Intent) {
        manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        manager.createNotificationChannel(
            notificationChannel
        )

        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        val intent2 = Intent(context, TodoActivity::class.java)
        val requestCode = intent?.extras!!.getInt("alarm_rqCode")
        val title = intent.extras!!.getString("content")

        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent2,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = builder.setContentTitle(title)
            .setContentText("Check your Task!")
            .setSmallIcon(R.drawable.app_logo_mini)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(1, notification)
    }
}