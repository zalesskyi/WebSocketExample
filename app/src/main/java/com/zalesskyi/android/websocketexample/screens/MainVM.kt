package com.zalesskyi.android.websocketexample.screens

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cleveroad.bootstrap.kotlin_rx_bus.RxBus
import com.zalesskyi.android.websocketexample.BaseVM
import com.zalesskyi.android.websocketexample.socket.events.MessageReceivedEvent
import com.zalesskyi.android.websocketexample.socket.events.MessageSentEvent
import com.zalesskyi.android.websocketexample.utils.ioToMainFlowable

class MainVM(application: Application) : BaseVM(application) {

    val messageLD = MutableLiveData<String>()

    fun init() {
        RxBus.filter(MessageReceivedEvent::class.java)
            .compose(ioToMainFlowable())
            .subscribe { messageLD.value = it.message }
            .addSubscription()
    }

    fun sendMessage(message: String) =
            RxBus.send(MessageSentEvent(message))
}