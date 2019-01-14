package com.fearaujo.mememaker.arch

import androidx.lifecycle.Lifecycle


interface BaseContract {

    interface View

    interface Presenter<V : BaseContract.View> {

        var view: V?

        val isViewAttached: Boolean

        fun attachLifecycle(lifecycle: Lifecycle)

        fun detachLifecycle(lifecycle: Lifecycle)

        fun attachView(view: V)

        fun detachView()

        fun onPresentCreated()

        fun onPresenterDestroy()


    }

}