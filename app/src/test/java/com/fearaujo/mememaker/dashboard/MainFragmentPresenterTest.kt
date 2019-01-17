package com.fearaujo.mememaker.dashboard

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainFragmentPresenterTest {

    @Mock
    lateinit var view: MainFragmentContract.View

    private lateinit var presenter: MainFragmentPresenter

    @Before
    fun setUp() {
        presenter = MainFragmentPresenter(view)
        presenter.attachView(view)
    }

    @Test
    fun `load admob on attach view`() {
        Mockito.verify(view).createAdView()
        //temp
        Mockito.verifyNoMoreInteractions(view)
    }
}