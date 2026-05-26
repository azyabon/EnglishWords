package com.azyabon.englishwords

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

// Unbound: Foreground service, Background Service
// Bound