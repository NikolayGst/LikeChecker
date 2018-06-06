package com.niko.likechecker.ui.like.fragments.scanner

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.extensions.logs
import com.niko.likechecker.model.Setting
import com.niko.likechecker.rx.checkLike
import com.niko.likechecker.rx.getAlbums
import com.niko.likechecker.rx.getFriends
import com.niko.likechecker.rx.getPhotos
import com.niko.likechecker.ui.common.BasePresenter
import com.niko.likechecker.utils.delaySecond
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ScannerPresenter : BasePresenter<ScannerView>() {

    fun startScanning(setting: Setting) {

        val peopleObservable = when (setting.peopleId) {
        //если 0, получаем у всех друзей их id
            0 -> getFriends(setting.vkUserId.toString())
                    .doOnNext { logs("users: " + it.size) }
                    .flatMap { Observable.fromIterable(it).map { it.id.toString() } }
        //иначе получаем id конкретного друга / человека
            else -> Observable.just(setting.peopleId.toString())
        }

        track(peopleObservable
                .delaySecond(1)
                .doOnNext { logs("user: $it") }
                //Получаем фото с профиля и стены и комбинируем
                .flatMap { peopleId ->
                    //получаем список альбомов и поэлементно перебираем их с задержкой в 1 секунду
                    getAlbums(peopleId).flatMap { Observable.fromIterable(it) }
                            .delaySecond(1)
                            .doOnNext { logs("album $it") }
                            .flatMap { getPhotos(peopleId, setting.time, it.id).flatMap { Observable.fromIterable(it) } }
                            .toList()
                            .toObservable()
                            .doOnNext { logs("photos: ${it.size}") }
                            //поэлементно перебираем фотографии
                            .flatMap { Observable.fromIterable(it) }
                            //с задержкой 1 сек
                            .delaySecond(1)
                            //проверяем каждую фотографию человека/друга на лайк от проверяемого юзера
                            .flatMap { checkLike(setting.vkUserId.toString(), peopleId, it) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewState::onLikeSearched, viewState::onErrorLoad, viewState::onLikeSearchedEnd));

    }

}


