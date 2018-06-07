package com.niko.likechecker.ui.main


import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.showDialog
import com.niko.likechecker.extensions.toast
import com.niko.likechecker.model.Friend
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.niko.likechecker.ui.dialog.DialogFriendFragment
import com.niko.likechecker.ui.like.LikeFriendActivity
import com.vk.sdk.VKSdk
import com.vk.sdk.api.model.VKApiUserFull
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* getAlbums(113124770.toString()).onErrorReturn { emptyList() }.concatMapIterable { it }
                .delaySecond(1)
                .doOnNext { logs("album $it") }
                .concatMap { getPhotos(113124770.toString(), 1525467600000L, it.id).onErrorReturn { emptyList() }.concatMapIterable { it } }
                .toList()
                .toObservable()
                .doOnNext { logs("photos: ${it.size}") }
                //поэлементно перебираем фотографии
                .concatMapIterable { it }
                //с задержкой 1 сек
                .delaySecond(1)
                //проверяем каждую фотографию человека/друга на лайк от проверяемого юзера
                .concatMap { checkLike("261550430", 113124770.toString(), it) }
                .doOnNext { logs("like ${it.url}") }
                .subscribe()*/

        selectFriend.setOnClickListener {
            mainPresenter.loadFriends()
        }

        checkLikesFriends.setOnClickListener {

            if (txtId.text.isEmpty()) return@setOnClickListener

            val id = when {
                txtId.text.toString().startsWith("id") -> txtId.text.toString().replace("id", "")
                else -> txtId.text.toString()
            }

            val intent = Intent(this, LikeFriendActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        exit.setOnClickListener {
            VKSdk.logout()
            finish()
        }
    }

    override fun onSuccessLoadProfile(vkUser: VKApiUserFull) {
        txtTitle.text = "${getString(R.string.textview_text_welcome)}, ${vkUser.first_name}!"
    }

    override fun onSuccessLoadFriends(friends: List<FriendItem>) {
        showDialog(DialogFriendFragment.newInstance(friends))
    }

    override fun onErrorLoad(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }

    @Subscribe
    fun onSuccessLoadFriend(friend: Friend) {
        txtId.setText("id${friend.id}")
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
