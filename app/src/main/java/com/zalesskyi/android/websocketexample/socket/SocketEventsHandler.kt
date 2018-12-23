package com.zalesskyi.android.websocketexample.socket

import com.cleveroad.bootstrap.kotlin_rx_bus.RxBus
import com.zalesskyi.android.websocketexample.socket.events.MessageReceivedEvent

object SocketEventsHandler {

    private val bus = RxBus

    fun onMessageReceived(message: String?) {
        message?.let { bus.send(MessageReceivedEvent(it)) }
    }
}