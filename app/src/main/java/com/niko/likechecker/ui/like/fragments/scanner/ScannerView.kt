package com.niko.likechecker.ui.like.fragments.scanner

import com.niko.likechecker.model.fastAdapterItems.PhotoItem
import com.niko.likechecker.ui.common.BaseView

interface ScannerView : BaseView {

     fun onLikeSearched(photo: PhotoItem)

     fun onLikeSearchedEnd()
}