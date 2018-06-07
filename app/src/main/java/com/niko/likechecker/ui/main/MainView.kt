package com.niko.likechecker.ui.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.ui.common.FriendView
import com.niko.likechecker.ui.common.ProfileView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : ProfileView, FriendView