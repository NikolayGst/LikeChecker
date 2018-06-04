package com.niko.likechecker.ui.like.fragments.setting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.toast
import com.niko.likechecker.model.Friend
import com.niko.likechecker.model.Setting
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import com.niko.likechecker.ui.common.FriendView
import com.niko.likechecker.ui.dialog.DialogFriendFragment
import com.niko.likechecker.utils.getMonth
import com.niko.likechecker.utils.getWeek
import kotlinx.android.synthetic.main.fragment_friends_settings.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FriendsSettingsFragment : MvpAppCompatFragment(), FriendView, View.OnClickListener {

    @InjectPresenter
    lateinit var settingsPresenter: SettingsPresenter

    private var time: Long = 0
    private var vkUserId = 1
    private var peopleId = 0

    companion object {
        fun newInstance(vkUserId: Int): FriendsSettingsFragment {
            val friendsSettingsFragment = FriendsSettingsFragment()
            friendsSettingsFragment.vkUserId = vkUserId
            return friendsSettingsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioAllDay.setOnClickListener(this)
        radioMonth.setOnClickListener(this)
        radioWeek.setOnClickListener(this)

        radioAll.setOnClickListener(this)
        radioPeople.setOnClickListener(this)

        btnScan.setOnClickListener { EventBus.getDefault().post(Setting(vkUserId, peopleId, time)) }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.radioAllDay -> time = 0
            R.id.radioWeek -> time = getWeek()
            R.id.radioMonth -> time = getMonth()
            R.id.radioAll -> {
                peopleId = 0
                lrEdit.visibility = View.GONE
                selectFriend.setOnClickListener(null)
            }
            R.id.radioPeople -> {
                lrEdit.visibility = View.VISIBLE
                selectFriend.setOnClickListener { settingsPresenter.loadFriends(vkUserId) }
            }
        }
    }

    override fun onSuccessLoadFriends(friends: List<FriendItem>) {
        DialogFriendFragment.newInstance(friends).show(fragmentManager, "dialog_friends")
    }

    override fun onErrorLoad(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }


    @Subscribe
    fun onSuccessLoadFriend(friend: Friend) {
        txtId.setText("id${friend.id}")
        peopleId = friend.id
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
