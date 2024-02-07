package com.aarav.shesaswiftieee.data

import androidx.media3.common.MediaItem

data class SWIFT(
    val title: String? = "",
    val mediaID: String? = "",
    val singer: String? ="",
    val album:String? = "",
    val songURL:String? ="",
    val imageURL:String?=""
)
fun MediaItem.toMusic() =
    SWIFT(
        album = mediaMetadata.albumTitle.toString(),                 // there has been a serious change here, from the github repo, songURL was put as mediaID
        mediaID = mediaMetadata.trackNumber.toString(),              // but I have tweaked it so that the track number is the mediaID.
        title = mediaMetadata.title.toString(),
        singer = mediaMetadata.artist.toString(),
        songURL = mediaId,
        imageURL = mediaMetadata.artworkUri.toString()

    )
