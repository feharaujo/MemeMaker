package com.fearaujo.mememaker.web

import com.fearaujo.mememaker.arch.BaseContract

interface WebSelectorContract : BaseContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<WebSelectorContract.View> {

    }

}