package com.example.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyNotification{
    companion object{
        public val  CHANNEL_ID = "Chanel1"
    }
    @SuppressLint("NewApi", "WrongConstant")
    constructor(context: Context, check: Boolean) {
        val intentActivity = Intent(context, MainActivity::class.java)
        val pendingContent = PendingIntent.getActivity(context, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT)
        val action:Notification.Action
        if (check) {
            val intent=Intent(context, MyBroastcastReceiver::class.java)
            intent.setAction("Play")
            val pendingAction=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            action = Notification.Action(R.drawable.ic_play_arrow_black_24dp,"Play",pendingAction)
        }else{
            val intent=Intent(context, MyBroastcastReceiver::class.java)
            intent.setAction("Pause")
            val pendingAction=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            action = Notification.Action(R.drawable.ic_pause_black_24dp,"Pause",pendingAction)
        }
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingContent)
                .setContentText("Sing my song")
                .setContentTitle("Play music")
                .addAction(action)
                .setStyle(
                    Notification.MediaStyle()
                        .setShowActionsInCompactView(0)
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        }else{
            notification = Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingContent)
                .setContentText("Sing my song")
                .setContentTitle("Play music")
                .addAction(action)
                .setStyle(
                    Notification.MediaStyle()
                        .setShowActionsInCompactView(0)
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        }
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1,notification)
    }
}