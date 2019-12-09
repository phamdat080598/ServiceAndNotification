package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var boundService: MyBoundService
    private  var id : Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStartService.setOnClickListener(this)
        btnStopService.setOnClickListener(this)
        btnPlay.setOnClickListener(this)
        id=resources.getIdentifier("music","raw",packageName)
        createNotificationChannel()
        createReceiver()
    }

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Phat nhạc"
            val description = "Phạm Văn Đạt"
            val importance = NotificationManager.IMPORTANCE_DEFAULT;
            val channel = NotificationChannel(MyNotification.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system;
            //you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService (NotificationManager::class.java);
            // Đăng kí Chanel cho notification
            notificationManager.createNotificationChannel(channel);
        }
    }
    private var boastcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.extras?.get("action_name")
            Log.i("Music","onReceive--2$action")
            when(action){
                "Play" -> id?.let { boundService.playMusic(it) }
                "Pause" -> boundService.pauseMusic()
            }
        }
    }
    fun createReceiver(){
        registerReceiver(boastcast, IntentFilter("Send"))
    }
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onClick(v: View?) {
        var intent = Intent(applicationContext,MyBoundService::class.java)
        when(v?.id){
            R.id.btnStartService -> bindService(intent,myConnectionService,Context.BIND_AUTO_CREATE)
            R.id.btnStopService -> unbindService(myConnectionService)
            R.id.btnPlay -> id?.let { boundService.playMusic(it) }
        }
    }
    
    var myConnectionService = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            Log.i("Music","onServiceDisconnected:$name")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("Music","onServiceConnected:$name")
            boundService = (service as MyBoundService.MyIBinder).getBoundService()
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(boastcast)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun A(){
        val notification: Notification = Notification.Builder(applicationContext, MyNotification.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentIntent(pendingContent)
            .setContentText("Sing my song")
            .setContentTitle("Play music")
//            .addAction(action)
//            .setStyle(
//                Notification.MediaStyle()
//                    .setShowActionsInCompactView(0))
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(1,notification)
    }
}
