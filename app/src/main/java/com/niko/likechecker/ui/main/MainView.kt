package com.niko.likechecker.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.niko.likechecker.model.Friend
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.vk.sdk.api.model.VKApiUserFull

@StateStrategyType(SingleStateStrategy::class)
interface MainView : MvpView {

    fun onSuccessLoadProfile(vkUser: VKApiUserFull)

    fun onSuccessLoadFriends(friends: List<FriendItem>)

    fun onErrorLoad(throwable: Throwable)
}