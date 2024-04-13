package com.aarav.shesaswiftieee.ui.screens.homeScreen.components

import com.aarav.shesaswiftieee.data.SWIFT

fun getSortedAlbum(data:List<SWIFT>): Map<String?, List<SWIFT>> {
    var _data = data.filterNotNull().filterNot { it.mediaID.isNullOrBlank() }

    return _data.groupBy { it.album }
}