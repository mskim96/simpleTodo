package com.msk.simpletodo.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class NotificationFunction(private val context: Context) {

    private lateinit var pendingIntent: PendingIntent

    fun callNotification(alarmCode: Int, content: String, time: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(context, NotificationReceiver::class.java)
        receiverIntent.apply {
            putExtra("alarm_rqCode", alarmCode)
            putExtra("content", content)
        }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarmCode,
                receiverIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val dateFormat = SimpleDateFormat("yyyy/MM/dd H:mm:ss", Locale("en", "ja"))
        val datetime = dateFormat.parse(time) as Date

        val calendar = Calendar.getInstance()
        calendar.time = datetime
        Log.d("TAG", "callNotification: ${calendar.time}")

        // calendar timeandMillis 에다가 시간 넣을 것임.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        );
    }

    fun cancelAlarm(alarmCode: Int) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        pendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarmCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager.cancel(pendingIntent)
    }
}