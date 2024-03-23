package com.aarav.shesaswiftieee.player.use_case

import com.aarav.shesaswiftieee.player.controller.PlaybackController
import com.aarav.shesaswiftieee.player.service.MusicPlaybackController
import javax.inject.Inject

class PlayMusicUseCase (private val playbackController: PlaybackController) {


    operator fun invoke(mediaItemsIndex: Int) {
        playbackController.play(mediaItemsIndex)

    }
}