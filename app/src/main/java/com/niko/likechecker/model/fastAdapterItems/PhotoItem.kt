package com.niko.likechecker.model.fastAdapterItems

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.niko.likechecker.R
import com.niko.likechecker.model.Photo
import com.niko.likechecker.utils.formatTime
import com.squareup.picasso.Picasso

class PhotoItem(val photo: Photo) : AbstractItem<PhotoItem, PhotoItem.ViewHolder>() {

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun getType() = PHOTO_ITEM

    override fun getLayoutRes() = R.layout.photo_item

    inner class ViewHolder(itemView: View) : FastAdapter.ViewHolder<PhotoItem>(itemView) {

        private val txtId = itemView.findViewById<TextView>(R.id.txtId)
        private var photo = itemView.findViewById<ImageView>(R.id.photo)
        private val txtDate = itemView.findViewById<TextView>(R.id.txtDate)
        private val txtItemId = itemView.findViewById<TextView>(R.id.txtItemId)

        override fun bindView(item: PhotoItem, payloads: List<Any>) {
            txtId.text = item.photo.friendId
            txtId.text = "user: id${item.photo.friendId}"
            txtItemId.text = "item: id${item.photo.id}"
            txtDate.text = "date: ${item.photo.date.formatTime()}"
            Picasso.get().load(item.photo.url).into(photo)
        }

        override fun unbindView(item: PhotoItem) {
            txtId.text = null
            txtId.text = null
            txtItemId.text = null
            txtDate.text = null
            photo.setImageDrawable(null)
        }
    }
}
