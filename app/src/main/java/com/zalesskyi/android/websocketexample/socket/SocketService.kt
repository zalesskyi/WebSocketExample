package com.zalesskyi.android.websocketexample.socket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.cleveroad.bootstrap.kotlin_rx_bus.RxBus
import com.neovisionaries.ws.client.*
import com.zalesskyi.android.websocketexample.extensions.printLog
import com.zalesskyi.android.websocketexample.extensions.printLogE
import com.zalesskyi.android.websocketexample.socket.events.MessageSentEvent
import com.zalesskyi.android.websocketexample.utils.ENDPOINT
import com.zalesskyi.android.websocketexample.utils.ioToMainFlowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SocketService : LifecycleService() {

    companion object {
        fun start(ctx: Context) {
            ctx.startService(Intent(ctx, SocketService::class.java))
        }

        fun stop(ctx: Context) {
            ctx.stopService(Intent(ctx, SocketService::class.java))
        }
    }

    private var webSocket: WebSocket? = null

    private val handler = SocketEventsHandler

    private val socketListener = object: WebSocketAdapter() {
        override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
            "socket connected".printLog()
        }

        override fun onTextMessage(websocket: WebSocket?, message: String?) {
            handler.onMessageReceived(message)
        }

        override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
            cause?.message.printLog()
            websocket?.reconnect()
        }

        override fun onDisconnected(
            websocket: WebSocket?,
            serverCloseFrame: WebSocketFrame?,
            clientCloseFrame: WebSocketFrame?,
            closedByServer: Boolean) {
            "socket disconnected".printLogE()
            if (closedByServer) websocket?.reconnect()
        }

        override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
            "pong frame: $frame".printLog()
            websocket?.sendPing()
        }
    }

    override fun onCreate() {
        super.onCreate()
        webSocket.takeIf { it != null }?.run {
            reconnect()
        } ?: initSocketConnection()
    }

    override fun onDestroy() {
        webSocket?.disconnect()
        super.onDestroy()
    }

    @SuppressLint("CheckResult")
    private fun initSocketConnection() {
        Single.just(Unit)
            .observeOn(Schedulers.io())
            .subscribe { t ->
                val factory = WebSocketFactory()
                webSocket = factory.createSocket(ENDPOINT).apply {
                    addListener(socketListener)
                    connect()
                }
            }
        initSendListener()
    }

    @SuppressLint("CheckResult")
    private fun initSendListener() {
        RxBus.filter(MessageSentEvent::class.java)
            .compose(ioToMainFlowable())
            .subscribe { webSocket?.sendText(it.message) }
    }

    private fun WebSocket.reconnect() {
        recreate().connect()
    }
}