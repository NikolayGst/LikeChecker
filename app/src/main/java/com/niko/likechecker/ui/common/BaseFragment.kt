package com.niko.likechecker.ui.common

import com.arellomobile.mvp.MvpAppCompatFragment
import com.niko.likechecker.extensions.logs
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : MvpAppCompatFragment() {
    private val compositeDisposable = CompositeDisposable()

    /**
     * Метод для сохранения подписок в коллекцию
     */
    protected fun track(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

   private fun clearObservables() {
        logs(" compositeDisposable.isDisposed(): " + compositeDisposable.isDisposed)
        //Если есть подписки, отписываем их
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearObservables()
    }
}
