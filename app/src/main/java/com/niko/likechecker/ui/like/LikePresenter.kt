package com.niko.likechecker.ui.like

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.rx.getUser
import com.niko.likechecker.ui.common.BasePresenter
import com.niko.likechecker.ui.common.ProfileView

@InjectViewState
class LikePresenter : BasePresenter<ProfileView>() {

    fun loadUserProfile(id: String) {
        track(getUser(id).subscribe(viewState::onSuccessLoadProfile, viewState::onErrorLoad))
    }

}