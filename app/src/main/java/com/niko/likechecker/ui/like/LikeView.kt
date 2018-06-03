package com.niko.likechecker.ui.like

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.ui.common.ProfileView

@StateStrategyType(SingleStateStrategy::class)
interface LikeView : ProfileView
