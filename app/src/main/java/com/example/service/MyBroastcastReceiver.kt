package com.example.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

open class MyBroastcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent("Send").putExtra("action_name",intent?.action))
        Log.i("Music","onReceive ${intent?.action}")
    }
}