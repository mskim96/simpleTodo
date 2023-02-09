package com.msk.simpletodo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NotificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not Implement")
    }
}