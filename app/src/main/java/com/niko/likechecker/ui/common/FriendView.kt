package com.niko.likechecker.ui.common

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.model.fastAdapterItems.FriendItem

@StateStrategyType(AddToEndSingleStrategy::class)
interface FriendView : BaseView {

    fun onSuccessLoadFriends(friends: List<FriendItem>)

}