package com.niko.likechecker.ui.like.fragments.setting

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.niko.likechecker.rx.getFriends
import com.niko.likechecker.ui.common.BasePresenter
import com.niko.likechecker.ui.common.FriendView
import io.reactivex.Observable

@InjectViewState
class SettingsPresenter : BasePresenter<FriendView>() {

    fun loadFriends(id: Int) {
        getFriends(id.toString())
                .flatMap { Observable.fromIterable(it).map(::FriendItem).toList().toObservable() }
                .subscribe(viewState::onSuccessLoadFriends, viewState::onErrorLoad)
    }

}