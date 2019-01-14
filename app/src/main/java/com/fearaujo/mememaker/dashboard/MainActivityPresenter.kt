package com.fearaujo.mememaker.dashboard

import android.Manifest
import com.fearaujo.mememaker.R
import com.fearaujo.mememaker.arch.BasePresenter

class MainActivityPresenter(override var view: MainActivityContract.View?) :
        BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    override fun shareContent() {
        view?.requestPermissions()
    }

    override fun onReceivePermissionResult(grantedPermissions: Collection<String>) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE in grantedPermissions &&
                Manifest.permission.READ_EXTERNAL_STORAGE in grantedPermissions) {
            view?.takeScreenshot()
        } else {
            view?.showErrorMessage(R.string.error_permission_required)
        }
    }

}
