package com.azyabon.englishwords

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

// Test service that counts seconds and logs them. It is used to test that the service is restarted after being killed by the system.
class CounterService : Service() {
    private var timer: CountDownTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        Log.d("CounterService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CounterService", "onStartCommand")

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            var count = 0

            override fun onTick(millisUntilFinished: Long) {
                count++
                Log.d("CounterService", "tick $count")
            }

            override fun onFinish() = Unit
        }.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CounterService", "onDestroy")
        timer?.cancel()
        timer = null
    }
}