package com.niko.likechecker.ui.main

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.extensions.logs
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.niko.likechecker.rx.getFriends
import com.niko.likechecker.rx.getProfileUser
import com.niko.likechecker.ui.common.BasePresenter
import io.reactivex.Observable

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        track(getProfileUser().doOnNext { logs("${it.first_name} ${it.last_name}") }
                .subscribe(viewState::onSuccessLoadProfile, viewState::onErrorLoad))
    }

     fun loadFriends() {
        track(getFriends()
                .flatMap { Observable.fromIterable(it).map(::FriendItem).toList().toObservable() }
                .subscribe(viewState::onSuccessLoadFriends, viewState::onErrorLoad))
    }

}