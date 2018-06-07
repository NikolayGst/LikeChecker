package com.niko.likechecker.ui.like.fragments.scanner

import com.arellomobile.mvp.InjectViewState
import com.niko.likechecker.extensions.logs
import com.niko.likechecker.model.Setting
import com.niko.likechecker.model.fastAdapterItems.PhotoItem
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

        clearObservables()

        val peopleObservable = when (setting.peopleId) {
        //если 0, получаем у всех друзей их id
            0 -> getFriends(setting.vkUserId.toString())
                    .doOnNext { logs("users: " + it.size) }
                    .concatMapIterable { it }
                    .map { it.id.toString() }
        //иначе получаем id конкретного друга / человека
            else -> Observable.just(setting.peopleId.toString())
        }

        track(peopleObservable
                .delaySecond(1)
                .concatMap { peopleId ->
                    //получаем список альбомов и поэлементно перебираем их с задержкой в 1 секунду
                    getAlbums(peopleId).onErrorReturn { emptyList() }.concatMapIterable { it }
                            .delaySecond(1)
                            .doOnNext { logs("album $it") }
                            .concatMap { getPhotos(peopleId, setting.time, it.id).onErrorReturn { emptyList() }.concatMapIterable { it } }
                            .toList()
                            .toObservable()
                            .doOnNext { logs("photos: ${it.size}") }
                            //поэлементно перебираем фотографии
                            .concatMapIterable { it }
                            //с задержкой 1 сек
                            .delaySecond(1)
                            .doOnNext { logs("photoId: ${it.id}") }
                            //проверяем каждую фотографию человека/друга на лайк от проверяемого юзера
                            .concatMap { checkLike(setting.vkUserId.toString(), peopleId, it).map(::PhotoItem) }
                }
                .doOnNext { logs("like: ${it.photo.url}") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewState::onLikeSearched, viewState::onErrorLoad, viewState::onLikeSearchedEnd))

    }

}


