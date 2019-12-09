package com.example.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast

class MyService : Service() {
    var media:MediaPlayer? = null
    var currentPossiton : Int = 0
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(applicationContext,"onCreate: ",Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext,"onStartCommand: $startId ---- $flags",Toast.LENGTH_LONG).show()
        if(startId==1){
            playMusic()
        }
       // return START_NOT_STICKY
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        media?.stop()
        Toast.makeText(applicationContext,"onDestroy: ",Toast.LENGTH_LONG).show()
    }

    fun playMusic(){
        var id=resources.getIdentifier("music","raw",packageName)
        media = MediaPlayer.create(applicationContext,id)
        media?.start()
        Toast.makeText(applicationContext,"Play Music",Toast.LENGTH_LONG).show()
        media?.setOnCompletionListener {
            stopSelf()
            Log.i("Music","Complete!!!!!!!!!")
        }
    }
}