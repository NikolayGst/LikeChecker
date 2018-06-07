package com.niko.likechecker.ui.like.fragments.scanner

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.model.fastAdapterItems.PhotoItem
import com.niko.likechecker.ui.common.BaseView

@StateStrategyType(SingleStateStrategy::class)
interface ScannerView : BaseView {
    fun onLikeSearched(photo: PhotoItem)

    fun onLikeSearchedEnd()
}