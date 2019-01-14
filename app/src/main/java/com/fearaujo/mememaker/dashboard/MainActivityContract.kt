package com.fearaujo.mememaker.dashboard

import com.fearaujo.mememaker.arch.BaseContract

interface MainActivityContract {

    interface View : BaseContract.View {
        fun requestPermissions()
        fun takeScreenshot()
        fun showErrorMessage(messageId: Int)
    }

    interface Presenter : BaseContract.Presenter<MainActivityContract.View> {
        fun shareContent()
        fun onReceivePermissionResult(grantedPermissions: Collection<String>)
    }

}