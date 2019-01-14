package com.fearaujo.mememaker.arch

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : Fragment(), BaseContract.View {

    val lifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    protected var presenter: P? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        if (isPresentCreated) {
            presenter?.onPresentCreated()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter?.detachLifecycle(lifecycleRegistry)
        presenter?.detachView()
    }

    private inline fun <reified T : BaseViewModel<V, P>> getViewModelType() = T::class.java

    protected abstract fun initPresenter(): P

}