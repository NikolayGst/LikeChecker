package com.niko.likechecker.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.model.Friend
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.niko.likechecker.ui.common.ProfileView
import com.vk.sdk.api.model.VKApiUserFull

@StateStrategyType(SingleStateStrategy::class)
interface MainView : ProfileView {

    fun onSuccessLoadFriends(friends: List<FriendItem>)

}