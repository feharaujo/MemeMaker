package com.fearaujo.mememaker.arch

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProviders


abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> :
        AppCompatActivity(), BaseContract.View {

    val lifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    protected var presenter: P? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isPresentCreated = false
        val type = getViewModelType<BaseViewModel<V, P>>()
        val viewModel: BaseViewModel<V, P> = ViewModelProviders.of(this).get(type)
        if (viewModel.presenter == null) {
            viewModel.presenter = initPresenter()
            isPresentCreated = true
        }

        presenter = viewModel.presenter
        presenter?.attachLifecycle(lifecycleRegistry)
        presenter?.attachView(this as V)

        if(isPresentCreated) {
            presenter?.onPresentCreated()
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachLifecycle(lifecycleRegistry)
        presenter?.detachView()
    }

    private inline fun <reified T : BaseViewModel<V, P>> getViewModelType() = T::class.java

    protected abstract fun initPresenter(): P

}