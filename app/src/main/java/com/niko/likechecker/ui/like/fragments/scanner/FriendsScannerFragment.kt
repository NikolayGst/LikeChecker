package com.niko.likechecker.ui.like.fragments.scanner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.logs
import com.niko.likechecker.extensions.toast
import com.niko.likechecker.model.Photo
import com.niko.likechecker.model.Setting
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FriendsScannerFragment : MvpAppCompatFragment(), ScannerView {

    @InjectPresenter
    lateinit var scannerPresenter: ScannerPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @Subscribe
    fun settingEvent(setting: Setting) {
        scannerPresenter.startScanning(setting)
        toast(setting.toString())
    }

    override fun onLikeSearched(photo: Photo) {
        logs("like: $photo")
    }

    override fun onLikeSearchedEnd() {
        toast("onLikeSearchedEnd")
    }

    override fun onErrorLoad(throwable: Throwable) {
       toast(throwable.localizedMessage)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }


}
