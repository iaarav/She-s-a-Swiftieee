package com.aarav.shesaswiftieee.components

import com.aarav.shesaswiftieee.data.SWIFT

fun getSortedAlbum(data:List<SWIFT>): Map<String?, List<SWIFT>> {
    return data.groupBy { it.album }
}