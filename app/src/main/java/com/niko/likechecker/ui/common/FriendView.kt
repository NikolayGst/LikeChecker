package com.niko.likechecker.ui.common

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.model.fastAdapterItems.FriendItem

@StateStrategyType(SingleStateStrategy::class)
interface FriendView : BaseView {

    fun onSuccessLoadFriends(friends: List<FriendItem>)

}