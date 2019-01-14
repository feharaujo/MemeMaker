package com.fearaujo.mememaker.arch

import androidx.lifecycle.ViewModel

class BaseViewModel<V: BaseContract.View, P: BaseContract.Presenter<V>>: ViewModel() {

    var presenter: P? = null

    override fun onCleared() {
        super.onCleared()

        presenter?.onPresenterDestroy()
        presenter = null
    }

}