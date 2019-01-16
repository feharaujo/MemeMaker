package com.fearaujo.mememaker.dashboard

import android.Manifest
import com.fearaujo.mememaker.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityPresenterTest {

    @Mock
    lateinit var view: MainActivityContract.View

    private lateinit var presenter: MainActivityPresenter

    @Before
    fun setUp() {
        presenter = MainActivityPresenter(view = view)
    }

    @Test
    fun `on share content action, check if the app has the required permissions`() {
        presenter.shareContent()

        verify(view).requestPermissions()
    }

    @Test
    fun `on receive positive feedback from runtime permissions`() {
        val permissionResults: Collection<String> = arrayListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        presenter.onReceivePermissionResult(permissionResults)

        verify(view).takeScreenshot()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `on receive negative feedback from runtime permissions`() {
        val permissionResults: Collection<String> = emptyList()
        presenter.onReceivePermissionResult(permissionResults)

        verify(view).showErrorMessage(R.string.error_permission_required)
        verifyNoMoreInteractions(view)
    }
}