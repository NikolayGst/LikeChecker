package com.niko.likechecker.ui.like

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.toast
import com.vk.sdk.api.model.VKApiUserFull
import kotlinx.android.synthetic.main.activity_like_friend.*

class LikeFriendActivity : MvpAppCompatActivity(), LikeView {

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
    }

    override fun onErrorLoad(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }
}
