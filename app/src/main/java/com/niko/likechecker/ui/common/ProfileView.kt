package com.niko.likechecker.ui.common

import com.arellomobile.mvp.MvpView
import com.vk.sdk.api.model.VKApiUserFull

interface ProfileView : MvpView {

    fun onSuccessLoadProfile(vkUser: VKApiUserFull)

    fun onErrorLoad(throwable: Throwable)

}