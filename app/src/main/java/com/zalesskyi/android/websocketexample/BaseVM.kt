package com.zalesskyi.android.websocketexample

import android.app.Application
import com.cleveroad.bootstrap.kotlin_core.ui.BaseLifecycleViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseVM(application: Application) : BaseLifecycleViewModel(application) {
    private var compositeDisposable: CompositeDisposable? = null

    protected fun Disposable.addSubscription() = addBackgroundSubscription(this)

    private fun addBackgroundSubscription(subscription: Disposable) {
        compositeDisposable?.apply {
            add(subscription)
        } ?: let {
            compositeDisposable = CompositeDisposable()
            compositeDisposable?.add(subscription)
        }
    }

}