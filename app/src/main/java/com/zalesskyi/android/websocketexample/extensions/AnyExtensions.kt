package com.zalesskyi.android.websocketexample.extensions

import androidx.annotation.StringRes
import com.zalesskyi.android.websocketexample.App
import com.zalesskyi.android.websocketexample.utils.EMPTY_STRING
import com.zalesskyi.android.websocketexample.utils.Logger

fun <T> T?.printLogE(text: String? = EMPTY_STRING, callLevel: Int = 1) = apply {
    Logger.run {
        e(message = { "$text ${this@printLogE}" }, callStackLevel = callLevel)
    }
}

fun <T> T?.printLog(text: String? = EMPTY_STRING, callLevel: Int = 1) = apply {
    Logger.run {
        d(message = { "$text ${this@printLog}" }, callStackLevel = callLevel)
    }
}

fun getStringApp(@StringRes stringId: Int) = App.instance.getString(stringId)

@Suppress("SpreadOperator")
fun getStringApp(@StringRes stringId: Int, vararg formatArgs: Any?) =
    App.instance.getString(stringId, *formatArgs)
