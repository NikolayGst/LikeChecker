package com.niko.likechecker.ui.common

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vk.sdk.api.model.VKApiUserFull

@StateStrategyType(SingleStateStrategy::class)
interface ProfileView : BaseView {

    fun onSuccessLoadProfile(vkUser: VKApiUserFull)

}