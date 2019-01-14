package com.fearaujo.mememaker.dashboard

import android.content.Intent
import android.graphics.Bitmap
import com.fearaujo.mememaker.arch.BaseContract

class MainFragmentContract: BaseContract {

    interface View: BaseContract.View {
        fun loadImageOnEditor(url: String)
        fun loadImageOnEditor(image: Bitmap)
        fun openGallery(title: String)
        fun openCamera()
    }

    interface Presenter : BaseContract.Presenter<MainFragmentContract.View> {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

}