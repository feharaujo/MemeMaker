package com.fearaujo.mememaker.dashboard

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.fearaujo.mememaker.R
import com.fearaujo.mememaker.arch.BaseActivity
import com.markodevcic.peko.Peko
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast

const val REQUEST_PHOTO_WEB = 50010
const val REQUEST_PHOTO_GALLERY = 50011
const val REQUEST_PHOTO_CAMERA = 50012
const val REQUEST_PHOTO_URL = "REQUEST_PHOTO_URL"

class MainActivity : BaseActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {

    override fun initPresenter(): MainActivityContract.Presenter {
        return MainActivityPresenter(this)
    }

    private val mainFragment: MainFragment by lazy {
        MainFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> presenter?.shareContent()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun requestPermissions() {
        val activity = this
        launch(UI) {
            val permissionResultDeferred = Peko.requestPermissionsAsync(
                    activity, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            )
            val (grantedPermissions) = permissionResultDeferred.await()
            presenter?.onReceivePermissionResult(grantedPermissions)
        }
    }

    override fun takeScreenshot() {
        mainFragment.takeScreenshot()
    }

    override fun showErrorMessage(messageId: Int) {
        toast(message = messageId)
    }

}
