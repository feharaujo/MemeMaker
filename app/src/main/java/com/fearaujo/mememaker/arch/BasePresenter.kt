package com.fearaujo.mememaker.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver


abstract class BasePresenter<V : BaseContract.View> : LifecycleObserver, BaseContract.Presenter<V> {

    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override val isViewAttached: Boolean
        get() = view != null

    override fun onPresenterDestroy() {

    }

    override fun onPresentCreated() {

    }

}