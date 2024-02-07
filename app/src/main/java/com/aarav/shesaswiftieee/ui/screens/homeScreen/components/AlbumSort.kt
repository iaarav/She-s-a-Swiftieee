package com.aarav.shesaswiftieee.ui.screens.homeScreen.components

import com.aarav.shesaswiftieee.data.SWIFT

fun getSortedAlbum(data:List<SWIFT>): Map<String?, List<SWIFT>> {
    return data.groupBy { it.album }
}