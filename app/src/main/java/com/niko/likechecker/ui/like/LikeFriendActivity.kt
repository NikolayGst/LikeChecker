package com.niko.likechecker.ui.like

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.toast
import com.niko.likechecker.model.Setting
import com.niko.likechecker.ui.common.ProfileView
import com.vk.sdk.api.model.VKApiUserFull
import kotlinx.android.synthetic.main.activity_like_friend.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class LikeFriendActivity : MvpAppCompatActivity(), ProfileView {

    @InjectPresenter
    lateinit var likePresenter: LikePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_friend)
        likePresenter.loadUserProfile(intent.extras.getString("id"))

    }

    override fun onSuccessLoadProfile(vkUser: VKApiUserFull) {
        toolbar.title = vkUser.first_name + " " + vkUser.last_name
        setSupportActionBar(toolbar)

        viewPager.adapter = TabAdapter(supportFragmentManager, vkUser.id, this)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onErrorLoad(throwable: Throwable) {
        toast(throwable.localizedMessage)
        finish()
    }

    @Subscribe
    fun settingEvent(setting: Setting) {
        viewPager.currentItem = 1
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
