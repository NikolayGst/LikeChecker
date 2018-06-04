package com.niko.likechecker.ui.main

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.ui.common.FriendView
import com.niko.likechecker.ui.common.ProfileView

@StateStrategyType(SingleStateStrategy::class)
interface MainView : ProfileView, FriendView