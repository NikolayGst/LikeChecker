package com.niko.likechecker.ui.like

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.rx.getProfileUser
import com.niko.likechecker.rx.getUser
import com.niko.likechecker.ui.common.BasePresenter

@InjectViewState
class LikePresenter : BasePresenter<LikeView>() {

    fun loadUserProfile(id: String) {
        track(getUser(id).subscribe(viewState::onSuccessLoadProfile, viewState::onErrorLoad))
    }

}