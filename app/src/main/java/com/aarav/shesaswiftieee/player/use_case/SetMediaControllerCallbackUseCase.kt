package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.data.PlayerState
import com.aarav.shesaswiftieee.data.SWIFT
import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class SetMediaControllerCallbackUseCase (private val playbackController: PlaybackController) {

    operator fun invoke(
        callback: (
            playerState: PlayerState,
            currentMusic: SWIFT?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOneEnabled: Boolean
        ) -> Unit
    ) {

            playbackController.mediaControllerCallback = callback

    }
}