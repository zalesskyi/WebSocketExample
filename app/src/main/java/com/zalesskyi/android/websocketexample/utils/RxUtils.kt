package com.zalesskyi.android.websocketexample.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Helper class for RxJava
 */
fun <T> ioToMain(observable: Observable<T>): Observable<T> = observable
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> ioToMain(single: Single<T>): Single<T> = single
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> ioToMain(flowable: Flowable<T>): Flowable<T> = flowable
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun completToMain(completable: Completable): Completable = completable
    .subscribeOn(Schedulers.computation())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> ioToMainSingle() = SingleTransformer<T, T> {
    it
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> ioToMainFlowable() = FlowableTransformer<T, T> { inObservable ->
    inObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> ioToMainObservable() = ObservableTransformer<T, T> {
    it
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
