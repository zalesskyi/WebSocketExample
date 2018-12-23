package com.zalesskyi.android.websocketexample

import android.app.Application
import com.zalesskyi.android.websocketexample.socket.SocketService

class App : Application() {
    companion object {
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun startSocketService() {
        SocketService.start(this)
    }

    fun stopSocketService() {
        SocketService.stop(this)
    }
}