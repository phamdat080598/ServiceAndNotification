package com.example.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MyBoundService : Service() {
    var media:MediaPlayer? = null
    var ibinder : IBinder = MyIBinder()
    var currentPossiton : Int = -1
    var idPrevious : Int = -2
    var idNext : Int=-1
    override fun onCreate() {
        super.onCreate()
        Log.i("Music","onCreate")
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.i("Music","onBind")
        return ibinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Log.i("Music","onUnbind")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Music","onDestroy")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("Music","onRebind")
    }
    inner class MyIBinder : Binder() {

        fun getBoundService() : MyBoundService{
            return this@MyBoundService
        }
    }

    fun playMusic(id:Int){
        idNext=id
        Log.i("Music","onPlay")
        if(this.idPrevious!=this.idNext) {
            media = MediaPlayer.create(applicationContext, id)
            Log.i("Music","media created")
            idPrevious=id
            media?.setOnCompletionListener {
                Log.i("Music", "Complete!!!!!!!!!")
            }
        }
        media?.start()
        MyNotification(applicationContext,false)
    }

    fun pauseMusic(){
        media?.pause()
        Log.i("Music","onPause")
        MyNotification(applicationContext,true)
    }

}