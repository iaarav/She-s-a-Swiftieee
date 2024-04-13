package com.aarav.shesaswiftieee.player.controller

import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT

interface PlaybackController {
    var mediaControllerCallback: ((
        playerState: PlayerState, currentMusic: SWIFT?, currentPosition: Long, totalDuration: Long, isShuffleEnabled: Boolean, isRepeatOneEnabled: Boolean
    ) -> Unit)?


    fun addMediaItems(musics: List<SWIFT>)
    fun play(mediaItemIndex: Int)
    fun resume()
    fun pause()
    fun seekTo(position: Long)
    fun skipNext()
    fun skipPrevious()
    fun setShuffleModeEnabled(isEnabled:Boolean)
    fun setRepeatOneEnabled(isEnabled:Boolean)
    fun getCurrentPosition():Long
    fun destroy()
    fun clear()
}