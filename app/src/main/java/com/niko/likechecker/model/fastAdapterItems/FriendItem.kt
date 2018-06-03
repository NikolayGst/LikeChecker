package com.niko.likechecker.model.fastAdapterItems

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.niko.likechecker.R
import com.niko.likechecker.model.Friend

class FriendItem(val friend: Friend) : AbstractItem<FriendItem, FriendItem.ViewHolder>() {

    override fun getType() = FRIEND_ITEM

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun getLayoutRes() = R.layout.friend_item

    inner class ViewHolder(view: View) : FastAdapter.ViewHolder<FriendItem>(view) {

        private val txtName = view.findViewById<TextView>(R.id.txtName)
        private val txtId = view.findViewById<TextView>(R.id.txtId)

        override fun bindView(item: FriendItem, payloads: MutableList<Any>) {
            txtName.text = item.friend.name
            txtId.text = item.friend.id.toString()
        }

        override fun unbindView(item: FriendItem) {
            txtName.text = null
            txtId.text = null

        }
    }
}