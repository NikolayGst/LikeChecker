package com.niko.likechecker.ui.like.fragments.scanner


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.toast
import com.niko.likechecker.model.Setting
import com.niko.likechecker.model.fastAdapterItems.PhotoItem
import kotlinx.android.synthetic.main.fragment_friends_scanner.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FriendsScannerFragment : MvpAppCompatFragment(), ScannerView {

    @InjectPresenter
    lateinit var scannerPresenter: ScannerPresenter

    private lateinit var itemAdapter: ItemAdapter<PhotoItem>
    private lateinit var fastAdapter: FastAdapter<PhotoItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ItemAdapter()

        fastAdapter = FastAdapter.with(itemAdapter)

        listPhotos.layoutManager = LinearLayoutManager(context)

        listPhotos.adapter = fastAdapter

        fastAdapter.withOnClickListener { _, _, item, _ ->
            val link = "http://vk.com/photo${item.photo.friendId}_${item.photo.id}"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            true
        }
    }

    @Subscribe
    fun settingEvent(setting: Setting) {
        itemAdapter.clear()
        scannerPresenter.startScanning(setting)
    }

    override fun onLikeSearched(photo: PhotoItem) {
        itemAdapter.add(photo)
    }

    override fun onLikeSearchedEnd() {
        toast("Конец работы парсера")
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
