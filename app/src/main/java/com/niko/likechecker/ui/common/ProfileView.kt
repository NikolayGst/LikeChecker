package com.niko.likechecker.ui.common

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vk.sdk.api.model.VKApiUserFull

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : BaseView {

    fun onSuccessLoadProfile(vkUser: VKApiUserFull)

}