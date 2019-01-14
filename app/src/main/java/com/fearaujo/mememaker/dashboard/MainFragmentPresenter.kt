package com.fearaujo.mememaker.dashboard

import android.content.Intent
import com.fearaujo.mememaker.arch.BasePresenter

class MainFragmentPresenter(override var view: MainFragmentContract.View?) :
        BasePresenter<MainFragmentContract.View>(), MainFragmentContract.Presenter {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}