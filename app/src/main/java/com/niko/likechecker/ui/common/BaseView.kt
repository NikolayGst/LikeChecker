package com.niko.likechecker.ui.common

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface BaseView : MvpView {

    fun showProgress()

    fun hideProgress()

    fun onErrorLoad(throwable: Throwable)
}