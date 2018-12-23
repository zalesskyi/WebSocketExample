package com.zalesskyi.android.websocketexample.screens

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cleveroad.bootstrap.kotlin_core.ui.BaseLifecycleActivity
import com.cleveroad.bootstrap.kotlin_core.ui.adapter.BaseListFragment.Companion.NO_ID
import com.google.android.material.snackbar.Snackbar
import com.zalesskyi.android.websocketexample.App
import com.zalesskyi.android.websocketexample.R
import com.zalesskyi.android.websocketexample.extensions.getStringApp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseLifecycleActivity<MainVM>() {
    override val containerId = NO_ID

    override val layoutId = R.layout.activity_main

    override val viewModelClass = MainVM::class.java

    override fun getProgressBarId() = NO_ID

    override fun getSnackBarDuration() = Snackbar.LENGTH_SHORT

    private val messageObserver = Observer<String> {
        tvEcho.text = getStringApp(R.string.echo_text, it)
    }

    override fun observeLiveData(viewModel: MainVM) {
        with (viewModel) {
            init()
            messageLD.observe(this@MainActivity, messageObserver)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.startSocketService()
        setupUi()
    }

    private fun setupUi() {
        bSend.setOnClickListener {
            viewModel.sendMessage(etMessage.text.toString())
        }
    }

    override fun onDestroy() {
        App.instance.stopSocketService()
        super.onDestroy()
    }
}